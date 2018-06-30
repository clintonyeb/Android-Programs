// Generated code from Butter Knife. Do not modify!
package com.example.clinton.homeflavour;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MainActivity$$ViewBinder<T extends MainActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131492983, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131492983, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131492984, "field 'fab'");
    target.fab = finder.castView(view, 2131492984, "field 'fab'");
    view = finder.findRequiredView(source, 2131492976, "field 'drawer'");
    target.drawer = finder.castView(view, 2131492976, "field 'drawer'");
    view = finder.findRequiredView(source, 2131492977, "field 'navigationView'");
    target.navigationView = finder.castView(view, 2131492977, "field 'navigationView'");
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends MainActivity> implements Unbinder {
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
      target.toolbar = null;
      target.fab = null;
      target.drawer = null;
      target.navigationView = null;
    }
  }
}
