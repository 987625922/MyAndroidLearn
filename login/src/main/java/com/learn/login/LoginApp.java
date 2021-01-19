package com.learn.login;

import android.app.Application;

import com.learn.componentbase.ServiceFactory;
import com.wyt.common.base.BaseApplication;

/**
 * @Author: LL
 * @Description:
 * @Date:Create：in 2021/1/19 15:02
 */
public class LoginApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initModuleApp(this);
        initModuleData(this);
    }

    @Override
    public void initModuleApp(Application application) {
        ServiceFactory.getInstance().setAccountService(new AccountService());
    }

    @Override
    public void initModuleData(Application application) {

    }
}
