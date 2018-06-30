// Generated code from Butter Knife. Do not modify!
package com.example.clinton.homeflavour;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class RegisterActivity$$ViewBinder<T extends RegisterActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131492969, "field 'mEmailView'");
    target.mEmailView = finder.castView(view, 2131492969, "field 'mEmailView'");
    view = finder.findRequiredView(source, 2131492970, "field 'mPasswordView'");
    target.mPasswordView = finder.castView(view, 2131492970, "field 'mPasswordView'");
    view = finder.findRequiredView(source, 2131492981, "field 'confirmPasswordView'");
    target.confirmPasswordView = finder.castView(view, 2131492981, "field 'confirmPasswordView'");
    view = finder.findRequiredView(source, 2131492982, "field 'signUpButton'");
    target.signUpButton = finder.castView(view, 2131492982, "field 'signUpButton'");
    view = finder.findRequiredView(source, 2131492979, "field 'loginTextView'");
    target.loginTextView = finder.castView(view, 2131492979, "field 'loginTextView'");
    view = finder.findRequiredView(source, 2131492980, "field 'userName'");
    target.userName = finder.castView(view, 2131492980, "field 'userName'");
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends RegisterActivity> implements Unbinder {
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
      target.confirmPasswordView = null;
      target.signUpButton = null;
      target.loginTextView = null;
      target.userName = null;
    }
  }
}
