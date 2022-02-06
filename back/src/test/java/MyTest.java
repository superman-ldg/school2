//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ldg.MainApplication;
//import com.ldg.controller.UserController;
//import com.ldg.pojo.Dynamic;
//import com.ldg.pojo.Goods;
//import com.ldg.pojo.User;
//import com.ldg.service.impl.DynamicServiceImpl;
//import com.ldg.service.impl.EmailServiceImpl;
//import com.ldg.service.impl.GoodsServiceImpl;
//import com.ldg.service.impl.UserServiceImpl;
//import com.ldg.service.impl.utils.JsonUtil;
//import com.ldg.service.impl.utils.MQRedis;
//import org.junit.runner.RunWith;
//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.junit.Test;
//
//import java.util.List;
//import java.util.UUID;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MainApplication.class)
//public class MyTest {
//
//    @Autowired
//    UserServiceImpl userService;
//    @Autowired
//    DynamicServiceImpl dynamicService;
//    @Autowired
//    GoodsServiceImpl goodsService;
//
//    @Autowired
//    JsonUtil jsonUtil;
//
//    @Autowired
//    MQRedis redisUtil;
//
//    @Autowired
//    ObjectMapper objectMapper;
//    @Autowired
//    RedisTemplate<String,Object> redisTemplate;
//
//    @Autowired
//    private AmqpAdmin rabbitAdmin;
//
//    @Autowired
//    private EmailServiceImpl emailService;
//    @Autowired
//    private UserController userController;
//
//    @Test
//    public void email(){
//        User user = userService.loadUser(1234567L, "string");
//        //int id_card = user.getId_card();
//        //System.out.println(id_card);
//        System.out.println("-------------------------------------");
//        System.out.println(user);
//        //emailService.sendSimpleMail("401528477@qq.com","招领小助手","明年今日");
//        //emailService.sendHtmlMail("1126458841@qq.com","test邮箱","您收到的邮箱信息");
//    }
//
//    @Test
//    public void testUser1(){
//        rabbitAdmin.deleteExchange("campus_ordinary_dynamic_exchange");
//        rabbitAdmin.deleteExchange("campus_dead_goods_exchange");
//        rabbitAdmin.deleteExchange("campus_dead_dynamic_exchange");
//        rabbitAdmin.deleteExchange("campus_ordinary_goods_exchange");
////        rabbitAdmin.deleteExchange("campus_ordinary_goods_exchange3);
////        rabbitAdmin.deleteExchange("campus.ordinary.dynamic.routingKey.#");
////        rabbitAdmin.deleteExchange("campus.ordinary.dynamic.routingKey.#");
////        rabbitAdmin.deleteExchange("campus_dead_exchange");
////        rabbitAdmin.deleteExchange("campus_ordinary_exchange");
////
////        rabbitAdmin.deleteQueue("campus_ordinary_dynamic_queue");
////        rabbitAdmin.deleteQueue("campus_dead_dynamic_queue");
////        rabbitAdmin.deleteQueue("campus_ordinary_goods_queue3");
////        rabbitAdmin.deleteQueue("campus_ordinary_goods_queue2");
////        rabbitAdmin.deleteQueue("campus_ordinary_goods_queue");
////        rabbitAdmin.deleteQueue("campus_dead_goods_queue");
//
//    }
//
//
//
////    @Autowired
////    MyJsonUtil myJsonUtil;
////
////    @Autowired
////    OutOrInConfigure outOrInConfigure;
//
//    @Test
//    public void testUser() throws JsonProcessingException, InterruptedException {
//
//       // User user = userService.queryUserById_CardNndPassword(11111211, "1222221111111111");
//       // System.out.println(user);
//
////        User user=new User();
////        user.setEmail("1111111111");
////        user.setId_card(11111211);
////        user.setName("1ee2e3113");
////        user.setPassword("1222221111111111");
////        int insert = userService.insert(user);
////        System.out.println("**************************");
////        System.out.println(insert);
////        System.out.println("***************************");
//
////        User user = userService.queryById(1L);
////        System.out.println(user);
////        List<User> users = userService.queryAll();
////        String s = objectMapper.writeValueAsString(users);
////        redisTemplate.opsForValue().set("user",s);
////        Thread.sleep(1000);
////        String s = jsonUtil.listToString(users);
////        System.out.println("-----------------");
////        System.out.println(s);
////        System.out.println("-----------------");
////        String user1 = (String)redisTemplate.opsForValue().get("user");
////        List list = jsonUtil.strToObject(user1,List.class);
////        list.forEach(System.out::println);
////        System.out.println(user1);
//    }
//    @Test
//    public void testDynamic(){
//        List<Dynamic> dynamics = dynamicService.queryDynamicAll();
//        dynamics.forEach(System.out::println);
//    }
//    @Test
//    public void testGoods(){
//        List<Goods> goods = goodsService.queryGoodsAll();
//        System.out.println(goods);
//
//    }
//
//    @Test
//    public void test(){
//        UUID uuid = UUID.randomUUID();
//        System.out.println("-----------------------"+uuid.toString());
//    }
//
////    @Test
////    public void jsonTest() throws IOException {
////        List<User> users = userService.queryAll();
////        System.out.println("总记录数:"+users.size());
////        BaseExport baseExport = new BaseExport();
////        baseExport.setExportDes("描述信息");
////        baseExport.setExportTime("日期");
////        baseExport.setExportCheck("检查码");
////        baseExport.setExportPerson("实施人员");
////        baseExport.setData(users);
////        baseExport.setTableMeaning("用户表");
////        baseExport.putTableField("id","主键");
////        baseExport.putTableField("id_card","学号");
////        baseExport.putTableField("name","年龄");
////        baseExport.putTableField("password","密码");
////        baseExport.putTableField("phone","电话");
////        baseExport.putTableField("email","邮箱");
////        baseExport.putTableField("status","身份");
////        baseExport.setTableName("user");
////        baseExport.addTableCLI_SETTING_NO("业务主键");
////        baseExport.setHOSPITAL_SOID(10000L);
////        baseExport.setTableCliSettingId("id");
////        long l = System.currentTimeMillis();
////        String s = myJsonUtil.dataToString(baseExport, false);
////        long l2 =System.currentTimeMillis();
////        System.out.println(s);
////        System.out.println("***************************************************************");
////        System.out.println("****************************************************************");
////        long l3 = System.currentTimeMillis();
////        BaseExport baseExport1 = myJsonUtil.stringToObject(s, BaseExport.class);
////        long l4 =System.currentTimeMillis();
////        System.out.println(baseExport);
////        System.out.println(baseExport1);
////        outOrInConfigure.inputData(s);
////        File file=new File("D:\\test.json");
////        if(!file.exists()){
////            file.createNewFile();
////        }
////        FileWriter fileWriter = new FileWriter(file);
////        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
////        bufferedWriter.write(s);
////        bufferedWriter.close();
////        fileWriter.close();
////        System.out.println("结束");
////        System.out.println("序列化时间:"+(l2-l));
////        System.out.println("反序列化时间:"+(l4-l3));
////
////    }
////
//
//
//}
