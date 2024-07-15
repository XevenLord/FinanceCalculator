package com.example.financecalculator.store;

import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

public class ViewModelStoreHolder implements ViewModelStoreOwner {
    private static final ViewModelStore viewModelStore = new ViewModelStore();
    private static final ViewModelStoreHolder holder = new ViewModelStoreHolder();

    private ViewModelStoreHolder() {}

    public static ViewModelStoreHolder getInstance() {
        return holder;
    }

    @Override
    public ViewModelStore getViewModelStore() {
        return viewModelStore;
    }
}
