package com.adee.myproject.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.adee.myproject.pojo.Address;
import com.adee.myproject.pojo.User;
import com.adee.myproject.pojo.User1;

/**
 * 1.ʹ��RequestMappingע�⽫�����url�ͷ�����Ӧ��
 * 2.��������ֵͨ����ͼ����������Ϊʵ�ʵ�������ͼ��InternalResourceViewResolver��ͼ�������������·�����
 * 	ͨ��prefix+returnVal+postfix��ʽ�õ�ʵ�ʵ�������ͼ����/WEB-INF/jsp/success.jsp��Ȼ����ת������
 * 3./helloworld��б��/��ʾ��ǰwebӦ�õ�Context-root���統ǰ��Ϊhello����ô�ͻ��������urlΪ��
 * 	������+webContextRoot+��Դ����Ŀ¼
 * 	http://localhost:8001/hello/helloworld
 * 	�������ǰwebӦ�õ�Context-root��Ϊ/����ô�ͻ��������urlΪ��
 * 	http://localhost:8001/helloworld
 * 4.RequestMapping�ȿ������η�����Ҳ���������࣬���η�����ʱ�򣬾ͽ�url����ָ��������
 * 	���������ʱ����ƥ�����·������ƥ�䷽����·��
 * 5.RequestMapping���˿���ʹ��urlӳ���⣬������ʹ��http�����value��method��params��headerӳ�䵽��򷽷��ϣ�
 * 	value�������url
 * 	method�����󷽷�
 * 	params������Ĳ���
 * 	headers������ͷ
 * 	����֮������Ĺ�ϵ������ʹ�ö����������������ӳ����Ӿ�ȷ����
 * 6.params��headers֧�ּ򵥱��ʽ
 * 	param1����ʾ������������Ϊparam1���������
 * 	!param1����ʾ�����ܰ�����Ϊparam1�Ĳ���
 * 	param1!=value1����ʾ���������Ϊparam1���������������ֵ����Ϊvalue1
 * 	{"param1=value1","param2"}����ʾ����������param1��param2������������param1�Ĳ���ֵ����Ϊvalue1
 * 7.Ant������Դ��ַƥ��
 * 	Ant�����Դ��ַƥ��֧��3��ƥ�����
 * 		-? ƥ���ļ����е�һ���ַ�
 * 		-* ƥ���ļ����е������ַ�
 * 		-** ƥ����·��
 * 	Ant����url��
 * 		-/user/* /createUser:ƥ��/user/aaa/createUser��/user/bbb/createUser��url
 * 		-/user/** /createUser��ƥ��/user/aaa/bbb/createUser��url
 * 		-/user/createUser??��ƥ��/user/createUseraa��/user/createUserbb��url
 * 8.@PathVariableӳ��url�󶨵�ռλ��
 * 	��ռλ����url��sping3.0�����Ĺ��ܣ��ù�����springmvc��RESTĿ��ͦ����չ�����о�����̱����塣
 * 	ͨ��@PathVariable���Խ�url��ռλ���󶨵Ĳ�������controller����������С�
 * 9.REST
 * 	REST��Representational State Transfer������Դ�����ֲ�״̬ת������Ŀǰ�����е�һ�ֻ���������ܹ������ṹ���������ϱ�׼���������
 * 	��������չ�����Եõ�Խ��Խ�����վ�Ĳ��á�
 * 	-��Դ��Resource���������ϵ�һ��ʵ�壬����˵�����ϵ�һ��������Ϣ����������һ���ı���һ��ͼƬ��һ�׸�����һ�ַ�����֮����һ������
 * 	�Ĵ��ڡ�����ʹ��һ��uriָ������ÿ����Դ��Ӧһ���ض���uri��Ҫ��ȡ�����Դ����������uri�Ϳ��ԡ����ÿ��uri��Ϊÿһ����Դ�Ķ�һ�޶���
 * 	��ʶ����
 * 	-���ֲ㣨Representation��������Դ������ֳ�������ʽ���������ı��ֲ㡣���磬�ı�������txt��ʽ���֣�Ҳ������html��ʽ��xml��ʽ��JSON
 * 	��ʽ���������Բ��ö����Ƹ�ʽ��
 * 	-״̬ת����State Transfer����ÿ����һ�����󣬾ʹ����˿ͻ��˺ͷ�������һ�ν������̡�httpЭ����һ����״̬��Э�飬�����е�״̬��
 * 	�����ڷ������ˡ���ˣ��ͻ�����Ҫ����������������ͨ��ĳ���ֶΣ��÷������˷�����״̬ת������������ת���ǽ����ڱ��ֲ�֮�ϵģ����ԣ�
 * 	���ǡ����ֲ�״̬ת����������˵������httpЭ�����棬�ĸ���ʾ������ʽ�Ķ���GET��POST��PUT��DELETE�����Ƿֱ��Ӧ���ֻ���������
 * 	GET������ȡ��Դ��POST�����½���Դ��PUT����������Դ��DELETE����ɾ����Դ��
 * 	ʹ��REST������ɾ�Ĳ�ʾ����
 * 	- /order/1 HTTP GET ���õ�id=1��order
 * 	- /order/1 HTTP DELETE ��ɾ��id=1��order
 * 	- /order/1 HTTP PUT ������id=1��order
 * 	- /order		HTTP POST������order
 * 10.HiddenHttpMethodFilter
 * 		�����form��ֻ֧��GET��POST���󣬶�DELETE��PUT��method����֧�֣�Spring3.0�����һ�����������Խ���Щ����ת��Ϊ��׼
 * 		��http������ʹ��֧��GET��POST��DELETE��PUT��
 * 11. springmvc����ǩ��
 * 		springmvcͨ��������������ǩ������http������Ϣ�󶨵�����������Ӧ����С�
 * 		springmvc�Կ�����������ǩ���������ǿ��ɵģ��������԰�ϲ�����κη�ʽ�Է�������ǩ����
 * 		��Ҫʱ���ԶԷ�����������α�ע��Ӧ��ע�⣨@PathVariable��@RequestParam��@RequestHeader�ȣ���springmvc��ܻὫ
 * 		http�������Ϣ�󶨵���Ӧ�ķ�������У������ݷ����ķ���ֵ����������Ӧ�ĺ�������
 * 		�ڴ�������δ�ʹ��@RequestParam���԰�����������ݸ����󷽷���
 * 			-value��������
 * 			-required���Ƿ���롣Ĭ��Ϊtrue����ʾ��������б��������Ӧ�Ĳ������������ڣ����׳��쳣��
 * 12.ʹ��@CookieValue�������е�cookieֵ
 * 		@CookieValue�����÷�����ΰ�ĳ��cookieֵ
 * 13.ʹ��POJO������������
 * 		springmvc�ᰴ�����������POJO�����������Զ�ƥ�䣬�Զ�Ϊ�ö����������ֵ��֧�ּ������ԡ��磺
 * 		dept.id��dept.address.tel��
 * 		Ĭ�ϻ�ӱ���ȡ��name-value����ֵ����������
 * 14.ʹ��Servletԭ��API
 * 		springmvc��controller�з������Խ�����ЩServletAPI���͵Ĳ�������
 * 			HttpServletRequest
 * 			HttpServletResponse
 * 			HttpSession
 * 			java.security.Principal
 * 			Locale
 * 			InputStream
 * 			OutputStream
 * 			Reader
 * 			Writer
 * 		��Щ���͵Ĳ���д�ڷ���������У��ͻ���springmvc�Զ����롣
 * 15.springmvc�ṩ��һ�¼���;�����ģ������
 * 		-ModelAndView����������ֵ����ΪModelAndViewʱ���������м���ͨ�������Ͷ������ģ������
 * 		-Map��Model�����Ϊorg.springframework.ui.Model��org.springframework.ui.ModelMap��
 * 									java.util.Mapʱ��map�е����ݻ��Զ���ӵ�����ֵModelAndView��
 * 		-@SessionAttributes����ģ���е�ĳ�������ݴ浽HttpSession�У�һ����������Թ����������
 * 		-@ModelAttribute��������α�ע��ע�����εĶ���ͻ�ŵ�����ģ��ModelAndView��
 * 16.@SessionAttributesע��
 * 		�������ϡ�
 * 		��ϣ���ڶ������֮�乫��ĳ��ģ���������ݣ�������ڿ�������һ��@SessionAttributes��Springmvc����ģ���ж�Ӧ�������ݴ浽
 * 		HttpSession�С�
 * 		@SessionAttributes���˿���ͨ��������ָ����Ҫ����Ự�е������⣬������ͨ��ģ�����ԵĶ�������ָ����Щģ��������Ҫ����
 * 		�Ự�С�
 * 		-@SessionAttributes(type=User.class)�Ὣ���������е���������ΪUser.class��������ӵ��Ự��
 * 		-@SessionAttributes(value={"user1", "user2"})
 * 		-@SessionAttributes(types={User.class, Dept.class})
 * 		-@SessionAttributes(value={"user1", "user2"}, types={Dept.class})
 *	17.@ModelAttribute
 *			1.��ÿ������ִ��ǰ�����ȵ��ã���@ModelAttributeע��ķ�����
 *			2.@ModelAttributeҲ��������Ŀ�귽����POJO���͵���Σ���value�������������ã�
 *				1��springmvc��ʹ��value����ֵ��implicitModel�в��Ҷ�Ӧ�Ķ������������ֱ�Ӵ���Ŀ�귽���������
 *				2��springmvc����value����ֵΪkey��POJO���͵Ķ���Ϊֵ�����뵽request��
 *	18.��ͼ����ͼ������
 *			- ��������ִ����ɺ����շ���һ��MocelAndView���󡣶�����Щ����String��View��ModelMap�����͵Ĵ�������springmvc
 *			Ҳ�����ڲ�������װ���һ��ModelAndView�������������߼�����ģ�Ͷ������ͼ��
 *			- springmvc������ͼ��������ViewResolver���õ����յ���ͼ����View�������յ���ͼ������jsp��Ҳ������Excel��JFreeChart��
 *			���ֱ�����ʽ����ͼ��
 *			- �������վ������ȡ������ͼ�����ģ�����ݽ�����Ⱦ���������������ģ������������ص�۽�������ģ�����ݵĹ����ϣ��Ӷ�ʵ��MVC
 *			�ĳ�ֽ��
 *	19.��ͼ
 *			- ��ͼ����������Ⱦģ�����ݣ���ģ�����������ĳ����ʽ���ָ��ͻ���
 *			- Ϊ��ʵ����ͼģ�ͺ;���ʵ�ּ����Ľ��Spring��org.springframework.web.servlet���ж�����һ���߶ȳ����View�ӿڣ�
 *			- ��ͼ��������ͼ����������ʵ������������ͼ����״̬�ģ��������ǲ������̰߳�ȫ���⡣
 *	20.������ͼʵ����
 *			1.URL��Դ��ͼ
 *				- InternalResourceView
 *					��jsp��������Դ��װ��һ����ͼ����InternalResourceViewResolverĬ��ʹ�õ���ͼʵ����
 *				- JstlView
 *					���jsp�ļ���ʹ����JSTL���ʻ���ǩ�Ĺ��ܣ�����Ҫʹ�ø���ͼ��
 *			2.�ĵ���ͼ
 *				- AbstractExcelView
 *					Excel�ĵ���ͼ�ĳ����࣬����ͼ����POI����Excel�ĵ�
 *				- AbstractPdfView
 * 				PDF�ĵ���ͼ�����࣬����ͼ����iText����PDF�ĵ�
 * 		3.������ͼ
 * 			���¼���ʹ��JasperReports����������ͼ��
 * 			- ConfigurableJsperReportsView
 * 			- JasperReportsCsvView
 * 			- JasperReportsMultiFormatView
 * 			- JasperReportsHtmlView
 * 			- JasperReportsPdfView
 * 			- JasperReportsXlsView
 * 		4.JSON��ͼ
 * 			- MappingJacksonJsonView
 * 				��ģ������ͨ��Jackson��Դ��ܵ�ObjectMapper��JSON��ʽ�����
 * 21.InternalResourceViewResolver
 * 		����Ŀ��ʹ����JSTL����springmvc���Զ�����ͼ��InternalResourceViewתΪJstlView��
 * 		��ʹ��JSTL��fmt��ǩ����Ҫ��springmvc�������ļ������ù��ʻ���Դ�ļ���
 * 		<bean id="messageResource" class="org.springframework.context.support.ResourceBundleMessageResource">
 * 			<property name="basename" value="i18n"/>
 * 		</bean>
 * 		��ϣ��ֱ����Ӧͨ��springmvc��Ⱦ��ҳ�棬����ʹ��mvc:view-controller��ǩʵ�֣�
 * 		<mvc:view-controller path="springmvc/testJstlView" view-name="success"/>
 * 22.������ͼ������ʵ����
 * 		1.����ΪBean������
 * 			- BeanNameViewResolver
 * 				���߼���ͼ������Ϊһ��Bean��Bean��id�����߼���ͼ��
 * 		2.����ΪURL�ļ�
 * 			- InternalResourceViewResolver
 * 				����ͼ������Ϊһ��URL�ļ���һ���ʹ�øý���������ͼ��ӳ��Ϊһ��������WEB-INF/Ŀ¼�µĳ����ļ�����jsp�ļ���
 * 			- JasperReportsViewResolver
 * 				JasperReports��һ������Java�Ŀ�Դ�����ߣ��ý���������ͼ������Ϊ�����ļ���Ӧ��URL
 * 		3.ģ���ļ���ͼ
 * 			- FreeMarkerViewResolver
 * 				����Ϊ����FreeMarkerģ�弼����ģ���ļ�
 * 			- VelocityViewResolver��VelocityLayoutViewResolver
 * 				����Ϊ����Velocityģ�弼����ģ���ļ�
 * 		- ����Ա����ѡ��һ����ͼ����������ö�����ͼ������
 * 		- ÿ����ͼ��������ʵ����Ordered�ӿڣ������ų�һ��order���ԣ�����ͨ��order����ָ��������������˳��orderԽС���ȼ�Խ�ߡ�
 * 		- springmvc�ᰴ��ͼ������������˳����߼���ͼ�����н�����ָ�������ɹ���������ͼ���󣬷�����׳�ServletException�쳣��
 * 23.�Զ�����ͼ-Excel��ͼ
 * 		- ��ϣ��ʹ��Excelչʾ��ͼ�б�������չspringmvc�ṩ��AbstractExcelView��AbstractJExcelView���ɡ�ʵ��buildExcelDocument
 * 			�������ڷ�����ʹ��ģ�����ݶ��󹹽�Excel�ĵ��Ϳ����ˡ�
 * 		- AbstractExcelView����POI API����AbstractJExcelView����JExcelAPI��
 * 		- ��ͼ������Ҫ����IOC������һ��Bean��ʹ��BeanNameViewResolver��Ϊ��ͼ���������ɡ�
 * 		- ��ϣ��ֱ�����������ֱ������Excel�ĵ��������������ӦͷContent-Disposition��ֵΪ��attachment;filename=xxx.xls���ɡ�
 * 24.�����ض���
 * 		- һ������£�controller�������ص�String���͵�ֵ�ᱻ�����߼���ͼ��������
 * 		- ������ص��ַ�����forward:��redirect:ǰ׺ʱ��springmvc������������⴦����forward:��redirect:����ָʾ���������ַ���
 * 			��ΪURL������
 * 				- redirect:success.jsp �� �����һ����success.jsp���ض������
 * 				- forward:success.jsp �� �����һ����success.jsp��ת������
 * 				
 * @return
 */
@Controller
@RequestMapping("/c01")
@SessionAttributes(types= {User.class})
public class Controller01 {
	
	private static final String SUCCESS = "success";
	
	@RequestMapping(	value="/**/m1", 
											method=RequestMethod.POST, 
											params= {"id", "name"},
											headers= {"Accept-Language=zh-CN,zh;q=0.9"}
										)
	public String m1() {
		System.out.println("/c01/m1");
		return SUCCESS;
	}
	
	@RequestMapping(	value="/**/m2/{id}/{name}", 
											method=RequestMethod.GET, 
											headers= {"Accept-Language=zh-CN,zh;q=0.9"}
										)
			public String m2(@PathVariable(value="id", required=true) Integer id, @PathVariable("name") String name) {
			System.out.println("/c01/m2 id:" + id + ", name:" + name);
			return SUCCESS;
	}
	
	//REST GET
	@RequestMapping(	value="/**/rest/{id}", method=RequestMethod.GET )
		public String m2(@PathVariable(value="id", required=true) Integer id) {
		System.out.println("rest get " + id);
		return SUCCESS;
	}
	//REST POST
	@RequestMapping(	value="/**/rest", method=RequestMethod.POST )
		public String m3() {
		System.out.println("rest post ");
		return SUCCESS;
	}
	//REST DELETE
	@RequestMapping(	value="/**/rest/{id}", method=RequestMethod.DELETE )
		public String m4(@PathVariable(value="id", required=true) Integer id) {
		System.out.println("rest delete " + id);
		return SUCCESS;
	}
	//REST PUT
	@RequestMapping(	value="/**/rest/{id}", method=RequestMethod.PUT )
		public String m5(@PathVariable(value="id", required=true) Integer id) {
		System.out.println("rest put " + id);
		return SUCCESS;
	}
	
	//param
	@RequestMapping(	value="/**/param", method=RequestMethod.POST )
		public String m6(@RequestParam(value="id", required=true) Integer id) {
		System.out.println("param id:" + id);
		return SUCCESS;
	}
	
	//@CookieValue
	@RequestMapping(	value="/**/cookievalue", method=RequestMethod.POST )
		public String m7(@CookieValue("JSESSIONID") String jsessionid) {
		System.out.println("jsessionid:" + jsessionid);
		return SUCCESS;
	}
	
	//POJO
	@RequestMapping(	value="/**/pojo", method=RequestMethod.POST )
		public String m8(User user) {
		System.out.println("user:" + user);
		return SUCCESS;
	}
	
	//ServletAPI
	@RequestMapping(	value="/**/servletapi", method=RequestMethod.POST )
		public void m9(HttpServletRequest request, HttpServletResponse response, 
				HttpSession session, Writer out) throws IOException {
		System.out.println("request:" + request);
		System.out.println("response:" + response);
		System.out.println("sessionid:" + session.getId());
		out.write("hello this is message from writer...");
		//return SUCCESS;
	}
	
	//ModelAndView
	@RequestMapping(	value="/**/modelandview", method=RequestMethod.POST )
		public ModelAndView m10() throws IOException {
		String viewName = "modelandview";
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("time", new Date());
		return mav;
	}
	@RequestMapping(	value="/**/modelandview01", method=RequestMethod.POST )
		public String m11(Map map) throws IOException {
		String viewName = "modelandview01";
		map.put("names", Arrays.asList("Tom", "Jerry", "Mike"));
		return viewName;
	}
	
	//@SessionAttributes
	@RequestMapping(	value="/**/sessionattributes", method=RequestMethod.POST )
		public String m12(Map<String, Object> map) throws IOException {
		User user = new User();
		user.setName("Baga");
		user.setPassword("123");
		user.setEmail("123@qq.com");
		Address add = new Address();
		add.setCity("langfang");
		add.setProvince("hebei");
		user.setAddress(add);
		map.put("user", user);
		return "sessionattributes";
	}
	
	//@ModelAttribute
	@ModelAttribute
	public String getUser1(@RequestParam(required=false) Integer id, Map<String, Object> map) throws IOException {
		if(id != null) {
			User1 user1 = new User1(1, "poppy", "123456", "12345678@qq.com", 12);
			System.out.println("�����ݿ��л�ȡһ��User1����" + user1);
			//�����ݿ��л�ȡһ��user1�������map�У���ʵ������implicitModel����
			map.put("abc", user1);
		}
		return SUCCESS;
	}
	
	/**
	 * @ModelAttribute��������
	 * 	1.ִ��@ModelAttribute���εķ����������ݿ�ȡ��user1���󣬲�����map�У�keyΪuser1
	 * 	2.springmvc��map��ȡ��user1���󣬰ѱ����������������user1����ĸ�������
	 * 	3.springmvc������������Ŀ�귽���Ĳ���
	 */
	@RequestMapping(	value="/**/user1/update", method=RequestMethod.POST )
		public String m13(User1 user1) throws IOException {
		System.out.println("�޸�user1��" + user1);
		return SUCCESS;
	}
	
	@RequestMapping(	value="/**/user1/modelattribute", method=RequestMethod.POST )
		public String m14(@ModelAttribute("abc") User1 user1) throws IOException {
		System.out.println("user1 @modelattribute��" + user1);
		return SUCCESS;
	}
	
	//�Զ�����ͼ
	@RequestMapping(	value="/**/helloView", method=RequestMethod.POST )
		public String m15() throws IOException {
		System.out.println("helloView");
		return "helloView";
	}	
	
	//�ض���
	@RequestMapping(	value="/**/redirect", method=RequestMethod.POST )
		public String m16() throws IOException {
		System.out.println("redirect");
		//return "redirect:/index.jsp";
		//return "redirect:/c01/helloView";
		return "forward:/c01/helloView";
	}		
	
	
	
	
}
