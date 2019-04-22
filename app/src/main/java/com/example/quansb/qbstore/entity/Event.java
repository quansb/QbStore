package com.example.quansb.qbstore.entity;

import java.io.Serializable;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean upDateShoppingCart =false;
    private boolean upDateAddress=false;

        public void upDateShoppingCart(boolean upDateShoppingCart){
         this.upDateShoppingCart =upDateShoppingCart;
        }

      public void upDateAddress(boolean upDateAddress){
            this.upDateAddress=upDateAddress;
      }

    public boolean isUpDateAddress() {
        return upDateAddress;
    }

    public void setUpDateAddress(boolean upDateAddress) {
        this.upDateAddress = upDateAddress;
    }

    public boolean isUpDateShoppingCart() {
        return upDateShoppingCart;
    }

    public void setUpDateShoppingCart(boolean upDateShoppingCart) {
        this.upDateShoppingCart = upDateShoppingCart;
    }
}
