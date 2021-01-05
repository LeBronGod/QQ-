package cn.lbg.pojo;

public interface Messagedao {

	String message_succeed="1";//表明是登陆成功
	String message_login_fail="2";//表明登录失败
	String message_comm_mes="3";//私聊信息包
	String message_get_onLineFriend="4";//要求在线好友的包
	String message_ret_onLineFriend="5";//返回在线好友的包
	String message_Regist_succeed="6";//表明注册成功
	String message_Regist_fail="7";//表明注册失败
	String message_comm_mes_group="8";//群聊信息包
	String message_get_Friendlist="9";//要求所有好友的信息
	String message_ret_Friendlist="10";//返回来所有好友的信息
	String message_get_grouplist="11";//要求所有已加入的群的信息
	String message_ret_grouplist="12";//返回所有已加入的群的信息
	String message_get_memberlist="13";//要求所有群成员的信息
	String message_ret_memberlist="14";//返回所有群成员的信息
	String message_Creategroup = "15";//创建群聊消息包
	String message_Creategroup_succeed = "16";//创建群聊成功
	String message_Creategroup_fail = "17";//创建群聊失败
	String message_Findfriend = "18";//找人
	String message_Findgroup = "19";//找群
	String message_Makefriend = "20";//加为好友
	String message_Joingroup = "21";//加入群聊
	String message_Clientleave = "22";//客户端下线
	String message_File = "23";//发送文件
	String message_Picture = "24";//发送图片
	String message_Voice = "25";//发送语音
	String message_get_Common_language = "27";//获取常用语
	String message_ret_Common_language = "28";//返还常用语
	String message_set_Common_language = "29";//设置常用语
	String message_breakfriend = "30";//删除好友
	String message_outgroup = "31";//退群
	String message_Notify = "32";//获取未读通知
	String message_removeNotify = "33";//删除已读通知
}
