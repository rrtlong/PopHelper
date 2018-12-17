package com.moli.pophelper.constant

/**
 * 项目名称：PopHelper
 * 类描述：
 * 创建人：lijilong
 * 创建时间：2018/12/4 14:37
 * 修改人：lijilong
 * 修改时间：2018/12/4 14:37
 * 修改备注：
 * @version
 */
object HelperArouter {
    object Activity {
        object MainActivity {
            const val PATH = "/home/Main"
        }

        object StrategyListActivity {
            const val PATH = "/list/strategy"
        }

        object GeneralWebActivity {
            const val PATH = "/web/general"
            const val TITLE = "title"
            const val URL = "url"
            const val IS_URL_COMPLETE = "isUrlComplete"
        }

        object LoginActivity {
            const val PATH = "/login/code"
        }

        object HelperAndFeedback {
            const val PATH = "/mine/feedback"
        }

        object SetActivity {
            const val PATH = "/mine/set"
        }

        object AboutUsActivity {
            const val PATH = "/mine/aboutus"
        }

        /**
         * 零钱记录
         */
        object ChangeRecordActivity {
            const val PATH = "/record/change"
            const val TYPE = "type" //0:零钱记录 1：钻石记录
        }

        /**
         * 体现
         */
        object GetCashActivity {
            const val PATH = "/money/getCash"
        }


    }

    object Fragment {
        object HomeFragment {
            const val PATH = "/main/home"
        }

        object WealthFragment {
            const val PATH = "/main/wealth"
        }

        object ActFragment {
            const val PATH = "/main/act"
        }

        object MineFragment {
            const val PATH = "/main/mine"
        }

        /**
         * 支付-菜单
         */
        object PayTypeList {
            const val PATH = "/main/payTypeList"
            const val PAY_DATA = "payData"
        }

        /**
         * 支付宝，微信支付fragment
         */
        object PayFragment {
            const val PATH = "/pay/fragment"
            const val ORDER_REQUEST = "orderRequest"
        }

        /**
         * 兑换钻石弹框fragment
         */
        object ExchangeDiamondFragment {
            const val PATH = "/exchange/diamond"
        }

    }
}
