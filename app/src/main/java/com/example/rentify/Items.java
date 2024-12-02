package com.example.rentify;


public class Items {
    private String _ID;
    private String _itemName;
    private String _itemDescription;
    private String _itemCategory;
    private Float _itemFee;
    private Float _itemTime;


    public Items() {
    }


    public Items(String id, String itemName, String description, String category, float fee, float time) {
        _ID = id;
        _itemName = itemName;
        _itemDescription = description;
        _itemCategory = category;
        _itemFee = fee;
        _itemTime = time;




    }
    public Items(String itemName, String description,String category, float fee, float time) {
        _itemName = itemName;
        _itemDescription = description;
        _itemCategory = category;
        _itemFee = fee;
        _itemTime = time;
    }


    public void setId(String id) {
        _ID = id;
    }
    public String getId() {
        return _ID;
    }
    public void setItemName(String _itemName) {
        this._itemName = _itemName;
    }
    public String getItemName() {
        return _itemName;
    }
    public void setItemDescription(String _description) {
        this._itemDescription = _description;
    }

    public String getItemDescription() {
        return _itemDescription;
    }
    public void setItemCategory(String _itemCategory) {
        this._itemCategory = _itemCategory;}
    public String getItemCategory() {
        return _itemCategory;
    }


    public void setItemFee(Float _itemFee) {
        this._itemFee = _itemFee;
    }


    public Float getItemFee() {
        return _itemFee;
    }


    public void setItemTime(Float _itemTime) {
        this._itemTime = _itemTime;
    }


    public Float getItemTime() {
        return _itemTime;
    }
}

