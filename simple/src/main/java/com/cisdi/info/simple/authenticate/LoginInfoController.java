package com.cisdi.info.simple.authenticate;

import com.cisdi.info.simple.dao.organization.OrganizationDao;
import com.cisdi.info.simple.dao.permission.OperatorDao;
import com.cisdi.info.simple.dao.qingTui.EmpOpenIdDao;
import com.cisdi.info.simple.dto.attachment.FileUploadDto;
import com.cisdi.info.simple.dto.operator.LoginDTO;
import com.cisdi.info.simple.dto.qingTui.BindingDto;
import com.cisdi.info.simple.entity.attachment.Attachment;
import com.cisdi.info.simple.entity.member.Member;
import com.cisdi.info.simple.entity.organization.Employee;
import com.cisdi.info.simple.entity.organization.Organization;
import com.cisdi.info.simple.entity.permission.LoginUser;
import com.cisdi.info.simple.entity.permission.ModuleTreeNode;
import com.cisdi.info.simple.entity.permission.Operator;
import com.cisdi.info.simple.entity.qingTui.EmpOpenId;
import com.cisdi.info.simple.entity.regist.EmployeRegist;
import com.cisdi.info.simple.entity.regist.OrganizationRegist;
import com.cisdi.info.simple.service.attachment.AttachmentService;
import com.cisdi.info.simple.service.base.BaseService;
import com.cisdi.info.simple.service.member.MemberService;
import com.cisdi.info.simple.service.organization.impl.EmployeeServiceBean;
import com.cisdi.info.simple.service.permission.ModuleService;
import com.cisdi.info.simple.service.permission.OperatorService;
import com.cisdi.info.simple.service.qingTui.impl.EmpOpenIdServiceBean;
import com.cisdi.info.simple.service.regist.EmployeRegistService;
import com.cisdi.info.simple.service.regist.OrganizationRegistService;
import com.cisdi.info.simple.util.MailUtil;
import com.cisdi.info.simple.util.NudgePlusConfig;
import com.cisdi.nudgeplus.sdk.service.OAuthService;
import com.cisdi.nudgeplus.tmsbeans.beans.UserInfoResult;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/Login")
@CrossOrigin(allowCredentials = "true")
public class LoginInfoController {
    String basePath = NudgePlusConfig.getValue("basePath");

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private OperatorService operatorService;
    @Autowired
    private OperatorDao operatorDao;
    @Autowired
    private BaseService baseService;

    @Autowired
    EmpOpenIdServiceBean empOpenIdService;
    @Autowired
    EmployeeServiceBean employeeService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private EmpOpenIdDao empOpenIdDao;
    @Autowired
    private OrganizationRegistService organizationRegistService;
    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private EmployeRegistService employeRegistService;
    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/loginSubmit")
    public Map<String,Object> loginSubmit(@Valid @RequestBody LoginDTO loginDTO) throws JSONException {
        Map<String,Object> result = this.operatorService.checkOperatorByUserNameAndPassWord(loginDTO);
        return  result;
    }

    @PostMapping("/getOrganizations")
    public Map<String, Object> getOrganization(@Valid @RequestBody LoginDTO loginDTO) {
        return this.operatorService.getOrganizations(loginDTO);
    }

    @GetMapping("/phoneLoginSubmit")
    public void phoneLoginSubmit(HttpServletResponse response) throws Exception{

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName("000");
        loginDTO.setPassword("670b14728ad9902aecba32e22fa4f6bd");
        Map<String,Object> result = this.operatorService.mobileCheckOperatorByUserNameAndPassWord(loginDTO);

        Boolean  isLogin = Boolean.parseBoolean(result.get("isLogin").toString());
        if(isLogin == true)
        {
            Long currentOperatorId = this.baseService.getCurrentLoginOperator().getEId();
            String userName = this.baseService.getCurrentLoginOperator().getName();
            response.sendRedirect("localhost:8091/#/main/"+currentOperatorId+"/"+URLEncoder.encode(userName,"UTF-8"));
        }
        else
        {

        }
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) throws JSONException{
        System.out.println("ddddd");
        JSONObject result = new JSONObject();
        result.put("state",true);
        result.put("result","");
        result.put("data","可以访问,用户当前组织为");

        return result.toString();
    }


    @PostMapping("/test2")
    public String test2( HttpServletRequest request ){
        Enumeration<String> headers = request.getHeaderNames();
        while(headers.hasMoreElements()){
            String name = headers.nextElement();
        }

        return "hello cloud";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        this.operatorService.logout();
        return "OK";
    }

    @GetMapping("/constructNewTree")
    public ModuleTreeNode constructNewTree(@RequestParam  Long operatorId, @RequestParam String moduleType) {
        ModuleTreeNode moduleTreeNode = this.moduleService.constructNewTree(operatorId, moduleType);
        return moduleTreeNode;
    }

    @PostMapping("/wisdomCateringLoginSubmit")
      public Map<String,Object> wisdomCateringLoginSubmit(@Valid @RequestBody LoginDTO loginDTO) throws JSONException {

        Map<String,Object> result = this.operatorService.checkMemberByUserNameAndPassWord(loginDTO);
        return  result;
    }
    @GetMapping("/wisdomCateringLogout")
    public String wisdomCateringLogout(HttpSession session){
        this.operatorService.wisdomCateringLogout();
        return "OK";
    }
    @GetMapping("/wisdomCateringConstructNewTree")
    public ModuleTreeNode wisdomCateringConstructNewTree(@RequestParam  Long operatorId, @RequestParam String moduleType) {
        return this.moduleService.wisdomCateringConstructNewTree(operatorId, moduleType);
    }
    //移动端模拟登录
     boolean mobileLoginSubmit(Long operatorId){
        Operator operator = this.operatorService.findOperator(operatorId);
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserName(operator.getCode());
        loginDTO.setPassword(operator.getPassWord());
        Map<String,Object> result = this.operatorService.mobileCheckOperatorByUserNameAndPassWord(loginDTO);//模拟登陆

        Boolean  isLogin = Boolean.parseBoolean(result.get("isLogin").toString());
        return  isLogin;
    }
    /**
     * 轻推入口
     */
    @GetMapping("/qingtuiInit")
    public void qingtuiInit(HttpServletResponse response,String qt_code) throws Exception {
        String appId = NudgePlusConfig.getValue("APP_ID");
        String appSercret = NudgePlusConfig.getValue("APP_SECRET");
        UserInfoResult userInfoResult = OAuthService.getUserInfo(appId, appSercret, qt_code);

        // 查询empOpenId
        EmpOpenId empOpenId = this.empOpenIdDao.findEmpOpenByOpenId(userInfoResult.getOpenid());
        if (empOpenId != null) {//不为空则定向到对应模块页面
            this.mobileLoginSubmit(empOpenId.getOperatorId());
            LoginUser loginUser = this.baseService.getLoginUser();
            if(empOpenId.getUserType().equals("商家")){
                Member member = this.memberService.findMember(empOpenId.getMemberId());
                response.sendRedirect(this.basePath + "main/" + empOpenId.getOperatorId()+"/"+ URLEncoder.encode(member.getName(),"UTF-8"));
            }
            else{
                Employee employee = this.employeeService.findEmployee(empOpenId.getEmployeeId());
                response.sendRedirect(this.basePath + "main/" + empOpenId.getOperatorId()+"/"+ URLEncoder.encode(employee.getName(),"UTF-8"));
            }
        }
        else {//为空则跳转到绑定页面
                response.sendRedirect(this.basePath + "login/" + userInfoResult.getUid()+"/"+ userInfoResult.getOpenid());
        }
    }

    /**
     * 绑定用户
     */
    @PostMapping("/bindingQingtui")
    public Map<String,Object> bindingQingtui(HttpServletResponse response,@Valid @RequestBody BindingDto bindingDto) {
        try {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUserName(bindingDto.getName());
            loginDTO.setPassword(bindingDto.getPassword());

            Map<String, Object> result = this.operatorService.mobileCheckOperatorByUserNameAndPassWord(loginDTO);//检查用户名和密码
            Boolean isCheck = Boolean.parseBoolean(result.get("isLogin").toString());
            if (isCheck) {
                Operator operator = this.operatorDao.findOperatorByCode(bindingDto.getName());
                EmpOpenId empOpenId = this.findEmpOpenIdByEmpIdAndOpenId(operator.getPersonId(), bindingDto.getOpenId());

                if (empOpenId != null) {
                    return this.dealMap(false, "该用户已经绑定","","");
                }
                EmpOpenId empOpenId1 = this.empOpenIdDao.findEmpOpenIdByOperatorId(operator.getEId());
                if(empOpenId1 != null){
                    return this.dealMap(false, "该账户已被绑定","","");
                }
                //新增绑定
                EmpOpenId newEmpOpenId = new EmpOpenId();
                newEmpOpenId.setUId(bindingDto.getuId());
                newEmpOpenId.setOpenId(bindingDto.getOpenId());
                newEmpOpenId.setOperatorId(operator.getEId());
                newEmpOpenId.setUserType(result.get("type").toString());
                newEmpOpenId.setType("轻推");

                if (result.get("type").equals("商家")) {
                    //绑定Member
                    Member member = this.memberService.findMember(operator.getPersonId());
                    newEmpOpenId.setMember(member);
                    this.empOpenIdService.saveEmpOpenId(newEmpOpenId);
                    //response.sendRedirect(this.basePath + "main/" + operator.getEId() + "/" + URLEncoder.encode(member.getName(), "UTF-8"));
                    return this.dealMap(false, "绑定成功",operator.getEId().toString(),member.getName());
                } else {
                    Employee employee = this.employeeService.findEmployee(operator.getPersonId());
                    newEmpOpenId.setEmployee(employee);
                    this.empOpenIdService.saveEmpOpenId(newEmpOpenId);

                    //response.sendRedirect(this.basePath + "main/" + operator.getEId() + "/" + URLEncoder.encode(employee.getName(), "UTF-8"));
                    return this.dealMap(false, "绑定成功",operator.getEId().toString(),employee.getName());
                }

            } else {
                return this.dealMap(false, "用户名或密码错误","","");
            }

        } catch (Exception e) {
            return this.dealMap(false, "绑定异常" + e.getMessage(),"","");
        }
    }

    public EmpOpenId findEmpOpenIdByEmpIdAndOpenId(Long employeeId, String openId) {
        EmpOpenId empOpenId = new EmpOpenId();
        empOpenId.setEmployeeId(employeeId);
        empOpenId.setOpenId(openId);
        return this.empOpenIdDao.findEmpOpenIdByEmpIdAndOpenId(empOpenId);
    }

    public Map<String,Object> dealMap(boolean returnState,String returnRemark,String operatorId,String name) {
        Map<String,Object>  map = new HashMap<String, Object>();
        map.put("returnState", returnState);//是否成功
        map.put("returnRemark", returnRemark);//说明
        if(!operatorId.equals("") && operatorId != null){
            map.put("operatorId",operatorId);
            map.put("name",name);
        }

        return map;
    }

    // 单位注册入口
    @PostMapping("/saveOrganizationRegist")
    public OrganizationRegist saveOrganizationRegist(@Valid @RequestBody OrganizationRegist organizationRegist){
        OrganizationRegist orgRegist =this.organizationRegistService.saveOrganizationRegist(organizationRegist);
        return orgRegist;
    }

    @GetMapping("/findOrganizationsWithIdNameByName")
    public Map<String,Object> findOrganizationsWithIdNameByName(@Valid @RequestParam String organizationName)
    {
        Map<String,Object>  map = new HashMap<String, Object>();
        Organization org = this.organizationDao.findOrganizationByName(organizationName);
       if(org != null){
           map.put("size",1);
       }
       else{
           map.put("size",0);
       }
        return map;
    }

    // 人员注册入口
    @PostMapping("/saveEmployeRegist")
    public void saveEmployeRegist(@RequestBody EmployeRegist employeRegist){
        EmployeRegist empRegist =this.employeRegistService.saveEmployeRegist(employeRegist);
    }

    @PostMapping("/saveAttachment")
    public void saveAttachment(FileUploadDto fileUploadDto, List<MultipartFile> files) throws IOException {
        Attachment a=new Attachment();
        String[] names = fileUploadDto.getName().split(",");

        a.setAssociateFormId(fileUploadDto.getAssociateFormId());//行id
        a.setAssociateFormName(fileUploadDto.getAssociateFormName());//表名
        for(int i=0;i<files.size();i++) {
            a.setRemark(names[i]);//获取文件时根据remark里面的值确定是哪一个文件
            Long id=attachmentService.uploadMultipartFile(files.get(i), a);
        }
    }

    @GetMapping("/sendEmailCode")
    public String sendEmailCode(@Valid @RequestParam String toEmail) throws MessagingException {
        MailUtil mail = new MailUtil();
        String emailCode = "";
        int intFlag = (int)(Math.random() * 1000000);//随机生成了6位数
        if(intFlag >= 9){
            emailCode = intFlag+"";
        }
        else{
            emailCode = intFlag+100000+"";
        }
        String from = "294567473@qq.com";
        String text = "<p>尊敬的用户：</p><p>您好!</p><p>您正在通过邮箱注册！"
                +"您的验证码是："+emailCode+"。</p><p>请妥善保管您的验证码。</p>";
        mail.send_mail(from,toEmail,text);
        return emailCode;
    }
}
