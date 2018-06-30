// Generated code from Butter Knife. Do not modify!
package com.example.clinton.homeflavour;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class RecoverPassword$$ViewBinder<T extends RecoverPassword> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131492969, "field 'mEmailView'");
    target.mEmailView = finder.castView(view, 2131492969, "field 'mEmailView'");
    view = finder.findRequiredView(source, 2131492978, "field 'passRequestBtn'");
    target.passRequestBtn = finder.castView(view, 2131492978, "field 'passRequestBtn'");
    view = finder.findRequiredView(source, 2131492979, "field 'loginTextView'");
    target.loginTextView = finder.castView(view, 2131492979, "field 'loginTextView'");
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends RecoverPassword> implements Unbinder {
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
      target.passRequestBtn = null;
      target.loginTextView = null;
    }
  }
}
