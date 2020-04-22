package com.project.mvvmframe.constant

/**
 * @CreateDate 2020/4/22 11:17
 * @Author jaylm
 */
object EventConst {
    //socket事件
    val EVENT_ENTRUST = "entrust" //挂单
    val EVENT_RECOMMEND = "recommend" //推荐
    val EVENT_KLINE = "kline" //K线
    val EVENT_TRADE_PAIR = "tradePair" //交易对
    val EVENT_CURRENT_ENTRUST = "currentEntrust" //当前委托
    val EVENT_LEVER_CURRENT_ENTRUST = "leverCurrentEntrust" //杠杆当前委托
    val EVENT_HISTORY_ENTRUST = "historyEntrust" //历史委托
    val EVENT_LEVER_HISTORY_ENTRUST = "leverHistoryEntrust" //杠杆历史委托
    val EVENT_LATEST_DEAL = "latestDeal"
    val EVENT_TRADE_AREA = "tradeArea" //币币交易区
    val EVENT_TRADE_PAIR_COLLECT = "tradePairCollect" //自选交易区
    val EVENT_LEVER_TRADE_AREA = "leverTradeArea" //杠杆交易区
    val EVENT_LEVER_RISK_RATE = "leverRiskRate" //杠杆平仓
}
