// Generated code from Butter Knife. Do not modify!
package com.fashiongallery.info;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view7f0a0120;

  private View view7f0a00e9;

  private View view7f0a0282;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.ivClose, "field 'ivClose' and method 'onViewClicked'");
    target.ivClose = Utils.castView(view, R.id.ivClose, "field 'ivClose'", ImageView.class);
    view7f0a0120 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.etMobile = Utils.findRequiredViewAsType(source, R.id.etMobile, "field 'etMobile'", EditText.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.etPassword, "field 'etPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.fabLogin, "field 'fabLogin' and method 'onViewClicked'");
    target.fabLogin = Utils.castView(view, R.id.fabLogin, "field 'fabLogin'", FloatingActionButton.class);
    view7f0a00e9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tvRegister, "field 'tvRegister' and method 'onViewClicked'");
    target.tvRegister = Utils.castView(view, R.id.tvRegister, "field 'tvRegister'", TextView.class);
    view7f0a0282 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivClose = null;
    target.etMobile = null;
    target.etPassword = null;
    target.fabLogin = null;
    target.tvRegister = null;

    view7f0a0120.setOnClickListener(null);
    view7f0a0120 = null;
    view7f0a00e9.setOnClickListener(null);
    view7f0a00e9 = null;
    view7f0a0282.setOnClickListener(null);
    view7f0a0282 = null;
  }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            