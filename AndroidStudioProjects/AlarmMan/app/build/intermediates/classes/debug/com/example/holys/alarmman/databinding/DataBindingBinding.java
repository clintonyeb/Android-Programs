package com.example.holys.alarmman.databinding;
import com.example.holys.alarmman.R;
import com.example.holys.alarmman.BR;
import android.view.View;
public class DataBindingBinding extends android.databinding.ViewDataBinding {
    
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    private final android.widget.LinearLayout mboundView0;
    private final android.widget.TextView mboundView1;
    private final android.widget.TextView mboundView2;
    // variables
    private com.example.holys.alarmman.User mUser;
    // values
    // listeners
    
    public DataBindingBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        final Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        setRootTag(root);
        invalidateAll();
    }
    
    @Override
    public void invalidateAll() {
        synchronized(this) {
            mDirtyFlags = 0x2L;
        }
        requestRebind();
    }
    
    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }
    
    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
            case BR.user :
                setUser((com.example.holys.alarmman.User) variable);
                return true;
        }
        return false;
    }
    
    public void setUser(com.example.holys.alarmman.User user) {
        updateRegistration(0, user);
        this.mUser = user;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        super.requestRebind();
    }
    public com.example.holys.alarmman.User getUser() {
        return mUser;
    }
    
    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeUser((com.example.holys.alarmman.User) object, fieldId);
        }
        return false;
    }
    private boolean onChangeUser(com.example.holys.alarmman.User user, int fieldId) {
        switch (fieldId) {
            case BR._all:
                synchronized(this) {
                    mDirtyFlags |= 0x1L;
                }
                return true;
        }
        return false;
    }
    
    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String firstNameUser = null;
        java.lang.String lastNameUser = null;
        com.example.holys.alarmman.User user = mUser;
    
        if ((dirtyFlags & 0x3L) != 0) {
            // read user~
            user = user;
            updateRegistration(0, user);
        
            if (user != null) {
                // read firstName~.~user~
                firstNameUser = user.firstName;
                // read lastName~.~user~
                lastNameUser = user.lastName;
            }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1
            this.mboundView1.setText(firstNameUser);
            this.mboundView2.setText(lastNameUser);
        }
    }
    // Listener Stub Implementations
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    
    public static DataBindingBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DataBindingBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<DataBindingBinding>inflate(inflater, com.example.holys.alarmman.R.layout.data_binding, root, attachToRoot, bindingComponent);
    }
    public static DataBindingBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DataBindingBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.example.holys.alarmman.R.layout.data_binding, null, false), bindingComponent);
    }
    public static DataBindingBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DataBindingBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/data_binding_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new DataBindingBinding(bindingComponent, view);
    }
}
    /* flag mapping
        flag 0: user~
        flag 1: INVALIDATE ANY
    flag mapping end*/
    //end