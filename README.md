1：本项目集成了springboot7+activti7+流程设计器一体

2：修改application.properties 里面数据库配置即可启动

3：设计器访问地址：http://localhost:port/activiti/create

4：接口地址：http://localhost:port/doc.html

4：关于表解释：

    Activiti的后台是有数据库的支持，所有的表都以ACT_开头。 第二部分是表示表的用途的两个字母标识。 用途也和服务的API对应。
    ACT_RE_*: 'RE'表示repository。 这个前缀的表包含了流程定义和流程静态资源 （图片，规则，等等）。
    ACT_RU_*: 'RU'表示runtime。 这些运行时的表，包含流程实例，任务，变量，异步任务，等运行中的数据。 Activiti只在流程实例执行过程中保存这些数据， 在流程结束时就会删除这些记录。 这样运行时表可以一直很小速度很快。
    ACT_ID_*: 'ID'表示identity。 这些表包含身份信息，比如用户，组等等。
    ACT_HI_*: 'HI'表示history。 这些表包含历史数据，比如历史流程实例， 变量，任务等等。
    ACT_GE_*: 通用数据， 用于不同场景下，如存放资源文件。
    资源库流程规则表
       1) act_re_deployment 部署信息表
       2) act_re_model  流程设计模型部署表
       3) act_re_procdef  流程定义数据表
    运行时数据库表
       1) act_ru_execution运行时流程执行实例表
       2) act_ru_identitylink运行时流程人员表，主要存储任务节点与参与者的相关信息
       3) act_ru_task运行时任务节点表
       4) act_ru_variable运行时流程变量数据表
    历史数据库表
    1) act_hi_actinst 历史节点表
    2) act_hi_attachment历史附件表
    3) act_hi_comment历史意见表
    4) act_hi_identitylink历史流程人员表
    5) act_hi_detail历史详情表，提供历史变量的查询
    6) act_hi_procinst历史流程实例表
    7) act_hi_taskinst历史任务实例表
    8) act_hi_varinst历史变量表
    通用数据表
    1) act_ge_bytearray二进制数据表
    2) act_ge_property属性数据表存储整个流程引擎级别的数据,初始化表结构时，会默认插入三条记录
    
    
    
    
    
