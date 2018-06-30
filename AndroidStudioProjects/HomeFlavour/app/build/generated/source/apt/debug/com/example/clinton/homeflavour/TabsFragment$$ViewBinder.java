// Generated code from Butter Knife. Do not modify!
package com.example.clinton.homeflavour;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class TabsFragment$$ViewBinder<T extends TabsFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131492995, "field 'tabs'");
    target.tabs = finder.castView(view, 2131492995, "field 'tabs'");
    view = finder.findRequiredView(source, 2131492996, "field 'viewPager'");
    target.viewPager = finder.castView(view, 2131492996, "field 'viewPager'");
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends TabsFragment> implements Unbinder {
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
      target.tabs = null;
      target.viewPager = null;
    }
  }
}
