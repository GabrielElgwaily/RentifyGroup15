package com.example.rentify;


public class CategoryEx {
    private String _id;
    private String _categoryname;
    private String _description;

    public CategoryEx() {
    }

    public CategoryEx(String id, String categoryname, String description) {
        _id = id;
        _categoryname = categoryname;
        _description = description;
    }
    public CategoryEx(String categoryname, String description) {
        _categoryname = categoryname;
        _description = description;
    }

    public void setId(String id) {
        _id = id;
    }
    public String getId() {
        return _id;
    }
    public void setCategoryName(String _categoryname) {
        this._categoryname = _categoryname;
    }
    public String getCategoryName() {
        return _categoryname;
    }
    public void setDescription(String _description) {
        this._description = _description;
    }
    public String getDescription() {
        return _description;
    }
}