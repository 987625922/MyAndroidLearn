package com.wyt.export_account

import com.alibaba.android.arouter.launcher.ARouter
import com.wyt.export_account.router.AccountRouter
import com.wyt.export_account.service.IAccountService

/**
 * @Author: LL
 * @Description: 共享工具
 * @Date:Create：in  2021/1/29 15:20
 */
object AccountServiceUtil {

    @JvmStatic
    fun getService(): IAccountService {
        return ARouter.getInstance().build(AccountRouter.PATH_SERVICE_ACCOUNT).navigation() as IAccountService
    }
}