# Myhealth
参考网课的SSM练手项目，健康平台的后端实现

关键技术：
1. 项目构建在**dubbo**分布式RPC框架上，基于zookeeper实现服务配置管理。
2. 基于Mybatis实现对检查项、检查组、套餐、预定信息等的CRUD功能的DAO层操作，部分功能在业务层面实现级联操作和事务管理，数据存储在**mysql**数据库。
3. 基于七牛云**对象存储服务(OBS)**实现对套餐图片的云端存储，并将上传图片集和落库图片集保存在**redis**中，基于**quartz**实现垃圾图片的定时清理。
4. 基于ApachePOI和JasperReporter处理报表的导入导出。
5. 模拟短信发送服务并基于redis+**cookie**实现登录验证与会话跟踪。
6. 使用**SpringSecurity**框架对请求进行过滤和权限校验。

文档整理：https://i2igfgkrnh.feishu.cn/docx/HeLndF5H9oKbf6xAQc3cXQ04nJd
