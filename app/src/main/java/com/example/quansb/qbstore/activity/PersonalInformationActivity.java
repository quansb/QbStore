package com.example.quansb.qbstore.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quansb.qbstore.R;
import com.example.quansb.qbstore.base.BaseActivity;
import com.example.quansb.qbstore.entity.UserInfo;

import com.example.quansb.qbstore.network.RequestCenter;
import com.example.quansb.qbstore.util.Constant;
import com.example.quansb.qbstore.util.Help;
import com.example.quansb.qbstore.util.JumpActivityUtil;
import com.example.quansb.qbstore.util.Logger;
import com.example.quansb.qbstore.util.PictureHelp;
import com.example.quansb.qbstore.util.PreferencesHelp;
import com.example.quansb.qbstore.util.ResolveTheLocalImageUri;
import com.example.quansb.qbstore.view.SelectDialog;
import com.mysdk.glide.GlideUtil;
import com.mysdk.logger.LoggerUtil;
import com.mysdk.okhttp.listener.DisposeDataListener;
import com.mysdk.util.StringUtils;
import com.mysdk.view.CircleImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.example.quansb.qbstore.util.Constant.AGE_RESULT_CODE;
import static com.example.quansb.qbstore.util.Constant.CROP_REQUEST_CODE;
import static com.example.quansb.qbstore.util.Constant.OPEN_CHOOSE_PHOTO_CODE;
import static com.example.quansb.qbstore.util.Constant.INFO_REQUEST_CODE;
import static com.example.quansb.qbstore.util.Constant.NICK_NAME_RESULT_CODE;
import static com.example.quansb.qbstore.util.Constant.PHOTO_CAMERA_CODE;
import static com.example.quansb.qbstore.util.Constant.USER_INFO;
import static com.example.quansb.qbstore.util.ResolveTheLocalImageUri.getImagePath;

public class PersonalInformationActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_back)
    TextView tvBack;
    @Bind(R.id.tv_common_centre)
    TextView tvCommonCentre;
    @Bind(R.id.common_top_layout)
    RelativeLayout commonTopLayout;
    @Bind(R.id.civ_head)
    CircleImageView civHead;
    @Bind(R.id.rl_head_img_layout)
    RelativeLayout rlHeadImgLayout;
    @Bind(R.id.rl_user_id_layout)
    RelativeLayout rlUserIdLayout;
    @Bind(R.id.rl_user_name_layout)
    RelativeLayout rlUserNameLayout;
    @Bind(R.id.rl_sex_layout)
    RelativeLayout rlSexLayout;
    @Bind(R.id.rl_age_layout)
    RelativeLayout rlAgeLayout;
    @Bind(R.id.rl_wallet_layout)
    RelativeLayout rlWalletLayout;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_user_id)
    TextView tvUserId;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_wallet)
    TextView tvWallet;
    @Bind(R.id.tv_confirm_change)
    TextView tvConfirmChange;
    private boolean loginStatus;
    private UserInfo userInfo;
    private int choice = 0;
    private Context context;
    private Uri imageUri;
    private  PictureHelp pictureHel;
    private  String cameraPhotoPath;

    @Override
    protected void initData() {
        context = this;
        refresh();
    }

    @Override
    protected void initView() {
        tvBack.setOnClickListener(this);
        tvBack.setText("");
        tvCommonCentre.setText(R.string.personal_information);
        civHead.setOnClickListener(this);
        rlAgeLayout.setOnClickListener(this);
        rlHeadImgLayout.setOnClickListener(this);
        rlSexLayout.setOnClickListener(this);
        rlWalletLayout.setOnClickListener(this);
        rlUserIdLayout.setOnClickListener(this);
        rlUserNameLayout.setOnClickListener(this);
        tvConfirmChange.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_information_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        refresh();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void refresh() {
        loginStatus = Help.isLogin(PersonalInformationActivity.this);
        if (!loginStatus) {
            tvUserName.setText("");
            tvUserId.setText("");
            civHead.setImageResource(R.drawable.ic_login);
            return;
        }
        PreferencesHelp preferencesHelp = new PreferencesHelp(PersonalInformationActivity.this);
        userInfo = (UserInfo) preferencesHelp.getObject(USER_INFO, UserInfo.class);
        GlideUtil.loadImageCircle(PersonalInformationActivity.this, userInfo.getAvatar_img(), civHead, 50);
        tvUserName.setText(userInfo.getUser_name());
        tvUserId.setText(userInfo.getUser_id());
        tvWallet.setText(userInfo.getMoney());
        tvAge.setText(userInfo.getAge());
        tvSex.setText(userInfo.getSex());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.rl_user_id_layout:    //修改用户id  不允许修改
                Toast.makeText(this, R.string.canno_change_user_id, Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_user_name_layout:     //修改用户昵称  允许修改 跳转到修改用户昵称界面
                JumpActivityUtil.goToNicknameActivity(this, userInfo.getUser_name());
                break;
            case R.id.rl_sex_layout:     //修改用户性别
                showSingDialog();
                break;
            case R.id.rl_age_layout:      //修改用户年龄  允许修改 跳转到修改用户年龄界面
                JumpActivityUtil.goToAgeChangeActivity(this, userInfo.getAge());
                break;
            case R.id.rl_wallet_layout:    //显示钱包余额
                break;
            case R.id.rl_head_img_layout:   //修改头像 弹出dialog
                showChangeHeadPictureDialog();
                break;
            case R.id.tv_confirm_change:
                ChangePersonalInformation();

                break;
        }
    }

    private void showChangeHeadPictureDialog() {
        final SelectDialog dialog = new SelectDialog().setShowBottom(true);
        dialog.show(getSupportFragmentManager());
        dialog.setOnSelectDialogClikeListener(new SelectDialog.onSelectDialogClickListener() {
            @Override
            public void onLocal() {


                    openAlbum();
                    dialog.dismiss();

            }

            @Override
            public void onCamera() {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.
                        WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(PersonalInformationActivity.this,      // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1); // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
                }
                else { //权限已经被授予

                        openCamera();
                }
                    dialog.dismiss();
            }

            @Override
            public void onCancel() {
                dialog.dismiss();
            }
        });


    }

    /**
     * 打开本地相册
     */
    private void openAlbum() {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.
                WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {   //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(PersonalInformationActivity.this,      // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1); // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
        }
        else {  //权限已经被授予
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent, OPEN_CHOOSE_PHOTO_CODE);
        }
    }

    private void openCamera() {
       cameraPhotoPath=getExternalCacheDir()+"/output_image.jpg";
        File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
        try {
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri=FileProvider.getUriForFile(this,"com.example.quansb.qbstore.fileprovider",outputImage);
        }else {
            imageUri=Uri.fromFile(outputImage);
        }
        //启动相机
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,PHOTO_CAMERA_CODE);
    }

    /**
     *    请求权限回调    相机权限 打开相册权限 回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(context, R.string.you_denied_permission, Toast.LENGTH_SHORT).show();
                }

                break;
            default:
        }
    }

    private void ChangePersonalInformation()                //修改个人信息 发送到服务器更新个人信息
    {
        String userName = tvUserName.getText().toString().trim();
        String age = tvAge.getText().toString().trim();
        String sex = tvSex.getText().toString().trim();
        String userId = tvUserId.getText().toString().trim();
        if (StringUtils.isEmpty(userName)) {
            Logger.showToastShort(getString(R.string.user_name_canno_null));
            return;
        }
        //
        RequestCenter.toChangePersonalInformation(userId, userName, age, sex, new DisposeDataListener() {
            @Override
            public void onSuccess(Object object) {
                updateUI();
                Toast.makeText(PersonalInformationActivity.this, R.string.modify_successfully, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Object object) {
                Toast.makeText(PersonalInformationActivity.this, R.string.fail_net, Toast.LENGTH_SHORT).show();
            }
        }, UserInfo.class);
    }

    private void updateUI() {           //把修改信息以对象的形式 保存到本地
        PreferencesHelp preferencesHelp = new PreferencesHelp(this);
        preferencesHelp.putObject(USER_INFO, userInfo);
    }

    private void showSingDialog() {
        final String[] items = {"男", "女", "保密"};
        AlertDialog.Builder Dialog = new AlertDialog.Builder(PersonalInformationActivity.this);
        //  singleChoiceDialog.setIcon(R.drawable.icon);
        Dialog.setTitle(R.string.change_sex);
        //第二个参数是默认的选项
        Dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice = which;
            }
        });
        Dialog.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choice != -1) {
                    switch (choice) {
                        case 0:
                            tvSex.setText(items[choice]);   //男

                            break;
                        case 1:
                            tvSex.setText(items[choice]);  //女

                            break;
                        case 2:
                            tvSex.setText(items[choice]);   //保密
                            break;
                    }
                    userInfo.setSex(tvSex.getText().toString().trim());
                }
                choice = 0;
            }
        });
        Dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        Dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case INFO_REQUEST_CODE:      //   跳转  activity 修改个人信息请求码
                switch (resultCode) {                        //通过结果码来判断 回调
                    case NICK_NAME_RESULT_CODE:           //回调 把在nickactivity 中修改的昵称保存到userinfo对象中 同时在个人信息中中显示修改后的名字
                        if (data != null) {
                            String nick_name = data.getStringExtra("nick_name");
                            tvUserName.setText(nick_name);
                            userInfo.setUser_name(nick_name);
                        }
                        break;
                    case AGE_RESULT_CODE:
                        if (data != null) {                                //同上  修改年龄
                            String age = data.getStringExtra("age");
                            tvAge.setText(age);
                            userInfo.setAge(age);
                        }
                        break;
                    default:
                        break;
                }
                break;
            case OPEN_CHOOSE_PHOTO_CODE:                //请求打开手机相册的请求码
                if (data != null) {
                    if (resultCode == RESULT_OK) {
                        //判断手机系统版本号
                        if (Build.VERSION.SDK_INT >= 19) {
                            //4.4以及以上系统使用这个方法
                            String imagePath = ResolveTheLocalImageUri.handleImageOnKitKat(data, context); // 4.4版本开始选取相册中的图片不再返回真实的uri  ////         解析图片真正的uri地址
                             pictureHel=new PictureHelp(this);
                             pictureHel.setAuthority("com.example.quansb.qbstore.fileprovider");
                           String cutPath=Environment.getExternalStorageDirectory().getPath()+
                                    "/cutcamera.png";
                            pictureHel.cutForPhoto(imagePath,cutPath,Constant.CROP_REQUEST_CODE);
                        } else {
                            //4.4以下系统使用这个方法
                            handleImageBeforeKitKat(data);
                        }
                    }
                }
                break;

            case PHOTO_CAMERA_CODE:  //打开相机 回调
                    if (resultCode==RESULT_OK){
                        try {
                            Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                            pictureHel=new PictureHelp(this);
                            pictureHel.setAuthority("com.example.quansb.qbstore.fileprovider");
                            pictureHel.cutForPhoto( cameraPhotoPath,Environment.getExternalStorageDirectory().getPath()+
                                    "/cutcamera.png",Constant.CROP_REQUEST_CODE);


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                break;

            case CROP_REQUEST_CODE:
           displayImage( pictureHel.getCutImgPath());
                break;
            default:
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null, context);
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {               //通过图片路径展示图片
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            civHead.setImageBitmap(bitmap);
        } else {
            Toast.makeText(context, R.string.failed_to_get_image, Toast.LENGTH_SHORT).show();
        }
    }





}
