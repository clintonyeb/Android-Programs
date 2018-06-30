// Generated code from Butter Knife. Do not modify!
package com.example.clinton.homeflavour;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class LoginActivity$$ViewBinder<T extends LoginActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131492969, "field 'mEmailView'");
    target.mEmailView = finder.castView(view, 2131492969, "field 'mEmailView'");
    view = finder.findRequiredView(source, 2131492970, "field 'mPasswordView'");
    target.mPasswordView = finder.castView(view, 2131492970, "field 'mPasswordView'");
    view = finder.findRequiredView(source, 2131492974, "field 'mSignUp'");
    target.mSignUp = finder.castView(view, 2131492974, "field 'mSignUp'");
    view = finder.findRequiredView(source, 2131492973, "field 'mSignInButton'");
    target.mSignInButton = finder.castView(view, 2131492973, "field 'mSignInButton'");
    view = finder.findRequiredView(source, 2131492972, "field 'checkBox'");
    target.checkBox = finder.castView(view, 2131492972, "field 'checkBox'");
    view = finder.findRequiredView(source, 2131492975, "field 'forgotTextView'");
    target.forgotTextView = finder.castView(view, 2131492975, "field 'forgotTextView'");
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends LoginActivity> implements Unbinder {
    private T target;

    protected InnerUnbinder(T target) {
      this.target = target;
    }

    @Override
    public final void unbind() {
      if (target == null) throw new IllegalStateException("Bindings already cleared.");
      unbind(target);
      target = null;
    }

    protected void unbind(T target) {
      target.mEmailView = null;
      target.mPasswordView = null;
      target.mSignUp = null;
      target.mSignInButton = null;
      target.checkBox = null;
      target.forgotTextView = null;
    }
  }
}
