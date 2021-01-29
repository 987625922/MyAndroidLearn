package com.learn.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wyt.export_account.AccountServiceUtil;
import com.wyt.export_account.router.AccountRouter;


/**
 * @Author: LL
 * @Description: 添加到路由，给其他module使用
 * @Date:Create：in 2021/1/19 15:06
 */
@Route(path = AccountRouter.PATH_FRAGMENT_ACCOUNT)
public class UserFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        TextView tvName = view.findViewById(R.id.tv_user_name);
        tvName.setText(AccountServiceUtil.getService().getUserInfo() == null ? "用户未登录" : "登录用户：" + AccountServiceUtil.getService().getUserInfo().getUserName());
        return view;
    }
}
