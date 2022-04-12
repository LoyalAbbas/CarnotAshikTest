package com.app.ashikcarnottest.ui

data class FilterItem(var name : String, var id : String, var type : String) {
    override fun toString(): String {
        if (name.equals(""))
            return "Select"
        return name
    }
}