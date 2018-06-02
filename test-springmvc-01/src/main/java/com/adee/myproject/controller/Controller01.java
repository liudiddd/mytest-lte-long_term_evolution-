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
 * 1.使用RequestMapping注解将请求的url和方法对应上
 * 2.方法返回值通过视图解析器解析为实际的物理视图，InternalResourceViewResolver视图解析器会做如下分析：
 * 	通过prefix+returnVal+postfix方式得到实际的物理视图，即/WEB-INF/jsp/success.jsp，然后做转发操作
 * 3./helloworld中斜杠/表示当前web应用的Context-root，如当前设为hello，那么客户端请求的url为：
 * 	服务器+webContextRoot+资源虚拟目录
 * 	http://localhost:8001/hello/helloworld
 * 	如果将当前web应用的Context-root设为/，那么客户端请求的url为：
 * 	http://localhost:8001/helloworld
 * 4.RequestMapping既可以修饰方法，也可以修饰类，修饰方法的时候，就将url给到指定方法。
 * 	而修饰类的时候，先匹配类的路径，再匹配方法的路径
 * 5.RequestMapping除了可以使用url映射外，还可以使用http请求的value、method、params及header映射到类或方法上：
 * 	value：请求的url
 * 	method：请求方法
 * 	params：请求的参数
 * 	headers：请求头
 * 	它们之间是与的关系，联合使用多个条件可以让请求映射更加精确化。
 * 6.params和headers支持简单表达式
 * 	param1：表示请求必须包含名为param1的请求参数
 * 	!param1：表示请求不能包含名为param1的参数
 * 	param1!=value1：表示请求包含名为param1的请求参数，但其值不能为value1
 * 	{"param1=value1","param2"}：表示请求必须包含param1和param2两个参数，且param1的参数值必须为value1
 * 7.Ant风格的资源地址匹配
 * 	Ant风格资源地址匹配支持3中匹配符：
 * 		-? 匹配文件名中的一个字符
 * 		-* 匹配文件名中的任意字符
 * 		-** 匹配多层路径
 * 	Ant风格的url：
 * 		-/user/* /createUser:匹配/user/aaa/createUser、/user/bbb/createUser等url
 * 		-/user/** /createUser：匹配/user/aaa/bbb/createUser等url
 * 		-/user/createUser??：匹配/user/createUseraa、/user/createUserbb等url
 * 8.@PathVariable映射url绑定的占位符
 * 	带占位符的url是sping3.0新增的功能，该功能在springmvc向REST目标挺近发展过程中具有里程碑意义。
 * 	通过@PathVariable可以将url中占位符绑定的参数传到controller方法的入参中。
 * 9.REST
 * 	REST即Representational State Transfer，（资源）表现层状态转化。是目前最流行的一种互联网软件架构。它结构清晰、符合标准、易于理解
 * 	、方便扩展，所以得到越来越多的网站的采用。
 * 	-资源（Resource）：网络上的一个实体，或者说网络上的一个具体信息。它可以是一段文本、一张图片、一首歌曲、一种服务，总之就是一个具体
 * 	的存在。可以使用一个uri指向它，每种资源对应一个特定的uri。要获取这个资源，访问它的uri就可以。因此每个uri即为每一个资源的独一无二的
 * 	标识符。
 * 	-表现层（Representation）：把资源具体呈现出来的形式，叫做它的表现层。比如，文本可以用txt格式表现，也可以用html格式、xml格式、JSON
 * 	格式，甚至可以采用二进制格式。
 * 	-状态转化（State Transfer）：每发出一个请求，就代表了客户端和服务器的一次交互过程。http协议是一个无状态的协议，即所有的状态都
 * 	保存在服务器端。因此，客户端想要操作服务器，必须通过某种手段，让服务器端发生“状态转化”。而这种转化是建立在表现层之上的，所以，
 * 	就是“表现层状态转化”。具体说，就是http协议里面，四个表示操作方式的动词GET、POST、PUT、DELETE。它们分别对应四种基本操作：
 * 	GET用来获取资源，POST用来新建资源，PUT用来更新资源，DELETE用来删除资源。
 * 	使用REST风格的增删改查示例：
 * 	- /order/1 HTTP GET ：得到id=1的order
 * 	- /order/1 HTTP DELETE ：删除id=1的order
 * 	- /order/1 HTTP PUT ：更新id=1的order
 * 	- /order		HTTP POST：新增order
 * 10.HiddenHttpMethodFilter
 * 		浏览器form表单只支持GET和POST请求，而DELETE、PUT等method并不支持，Spring3.0添加了一个过滤器可以将这些请求转换为标准
 * 		的http方法，使得支持GET、POST、DELETE和PUT。
 * 11. springmvc方法签名
 * 		springmvc通过分析处理方法的签名，将http请求信息绑定到处理方法的相应入参中。
 * 		springmvc对控制器处理方法签名的限制是宽松的，几乎可以按喜欢的任何方式对方法进行签名。
 * 		必要时可以对方法及方法入参标注相应的注解（@PathVariable、@RequestParam、@RequestHeader等），springmvc框架会将
 * 		http请求的信息绑定到相应的方法入参中，并根据方法的返回值类型做出相应的后续处理。
 * 		在处理方法入参处使用@RequestParam可以把请求参数传递给请求方法：
 * 			-value：参数名
 * 			-required：是否必须。默认为true，表示请求参数中必须包含对应的参数，若不存在，将抛出异常。
 * 12.使用@CookieValue绑定请求中的cookie值
 * 		@CookieValue可以让方法入参绑定某个cookie值
 * 13.使用POJO对象绑定请求参数
 * 		springmvc会按请求参数名和POJO属性名进行自动匹配，自动为该对象填充属性值。支持级联属性。如：
 * 		dept.id，dept.address.tel等
 * 		默认会从表单中取出name-value，赋值给参数对象
 * 14.使用Servlet原生API
 * 		springmvc中controller中方法可以接收哪些ServletAPI类型的参数？：
 * 			HttpServletRequest
 * 			HttpServletResponse
 * 			HttpSession
 * 			java.security.Principal
 * 			Locale
 * 			InputStream
 * 			OutputStream
 * 			Reader
 * 			Writer
 * 		这些类型的参数写在方法的入参中，就会由springmvc自动传入。
 * 15.springmvc提供了一下几种途径输出模型数据
 * 		-ModelAndView：方法返回值类型为ModelAndView时，方法体中即可通过该类型对象添加模型数据
 * 		-Map及Model：入参为org.springframework.ui.Model、org.springframework.ui.ModelMap或
 * 									java.util.Map时，map中的数据会自动添加到返回值ModelAndView中
 * 		-@SessionAttributes：将模型中的某个属性暂存到HttpSession中，一便多个请求可以共享这个属性
 * 		-@ModelAttribute：方法入参标注该注解后，入参的对象就会放到数据模型ModelAndView中
 * 16.@SessionAttributes注解
 * 		用于类上。
 * 		若希望在多个请求之间公用某个模型属性数据，则可以在控制类上一个@SessionAttributes，Springmvc将在模型中对应的属性暂存到
 * 		HttpSession中。
 * 		@SessionAttributes除了可以通过属性名指定需要放入会话中的属性外，还可以通过模型属性的对象类型指定哪些模型属性需要放入
 * 		会话中。
 * 		-@SessionAttributes(type=User.class)会将隐含类型中的所有类型为User.class的属性添加到会话中
 * 		-@SessionAttributes(value={"user1", "user2"})
 * 		-@SessionAttributes(types={User.class, Dept.class})
 * 		-@SessionAttributes(value={"user1", "user2"}, types={Dept.class})
 *	17.@ModelAttribute
 *			1.在每个方法执行前都会先调用，有@ModelAttribute注解的方法。
 *			2.@ModelAttribute也可以修饰目标方法的POJO类型的入参，其value属性有如下作用：
 *				1）springmvc会使用value属性值在implicitModel中查找对应的对象，若存在则会直接传入目标方法的入参中
 *				2）springmvc会以value属性值为key，POJO类型的对象为值，存入到request中
 *	18.视图和视图解析器
 *			- 请求处理方法执行完成后，最终返回一个MocelAndView对象。对于那些返回String、View或ModelMap等类型的处理方法，springmvc
 *			也会在内部将它们装配城一个ModelAndView对象，它包含了逻辑名和模型对象的视图。
 *			- springmvc借助视图解析器（ViewResolver）得到最终的视图对象（View），最终的视图可以是jsp，也可能是Excel、JFreeChart等
 *			各种表现形式的视图。
 *			- 对于最终究竟会采取何种视图对象对模型数据进行渲染，处理器并不关心，处理器工作重点聚焦在生产模型数据的工作上，从而实现MVC
 *			的充分解耦。
 *	19.视图
 *			- 视图的作用是渲染模型数据，将模型里的数据以某种形式呈现给客户。
 *			- 为了实现视图模型和具体实现技术的解耦，Spring在org.springframework.web.servlet包中定义了一个高度抽象的View接口：
 *			- 视图对象由视图解析器负责实例化。由于视图是无状态的，所以它们不会有线程安全问题。
 *	20.常用视图实现类
 *			1.URL资源视图
 *				- InternalResourceView
 *					将jsp或其他资源封装成一个视图，是InternalResourceViewResolver默认使用的视图实现类
 *				- JstlView
 *					如果jsp文件中使用了JSTL国际化标签的功能，则需要使用该视图了
 *			2.文档视图
 *				- AbstractExcelView
 *					Excel文档视图的抽象类，该视图基于POI构造Excel文档
 *				- AbstractPdfView
 * 				PDF文档视图抽象类，该视图基于iText构造PDF文档
 * 		3.报表视图
 * 			以下几个使用JasperReports报表技术的视图：
 * 			- ConfigurableJsperReportsView
 * 			- JasperReportsCsvView
 * 			- JasperReportsMultiFormatView
 * 			- JasperReportsHtmlView
 * 			- JasperReportsPdfView
 * 			- JasperReportsXlsView
 * 		4.JSON视图
 * 			- MappingJacksonJsonView
 * 				将模型数据通过Jackson开源框架的ObjectMapper以JSON方式输出。
 * 21.InternalResourceViewResolver
 * 		若项目中使用了JSTL，则springmvc会自动把视图由InternalResourceView转为JstlView。
 * 		若使用JSTL的fmt标签则需要在springmvc的配置文件中配置国际化资源文件。
 * 		<bean id="messageResource" class="org.springframework.context.support.ResourceBundleMessageResource">
 * 			<property name="basename" value="i18n"/>
 * 		</bean>
 * 		若希望直接响应通过springmvc渲染的页面，可以使用mvc:view-controller标签实现：
 * 		<mvc:view-controller path="springmvc/testJstlView" view-name="success"/>
 * 22.常用视图解析器实现类
 * 		1.解析为Bean的名字
 * 			- BeanNameViewResolver
 * 				将逻辑视图名解析为一个Bean，Bean的id等于逻辑视图名
 * 		2.解析为URL文件
 * 			- InternalResourceViewResolver
 * 				将视图名解析为一个URL文件，一般会使用该解析器将视图名映射为一个保存在WEB-INF/目录下的程序文件（如jsp文件）
 * 			- JasperReportsViewResolver
 * 				JasperReports是一个基于Java的开源报表工具，该解析器将视图名解析为报表文件对应的URL
 * 		3.模板文件视图
 * 			- FreeMarkerViewResolver
 * 				解析为基于FreeMarker模板技术的模板文件
 * 			- VelocityViewResolver、VelocityLayoutViewResolver
 * 				解析为基于Velocity模板技术的模板文件
 * 		- 程序员可以选择一种视图解析器或混用多种视图解析器
 * 		- 每个视图解析器都实现了Ordered接口，并开放出一个order属性，可以通过order属性指定解析器的优先顺序，order越小优先级越高。
 * 		- springmvc会按视图解析器的优先顺序对逻辑视图名进行解析，指到解析成功并返回视图对象，否则会抛出ServletException异常。
 * 23.自定义视图-Excel视图
 * 		- 若希望使用Excel展示视图列表，仅需扩展springmvc提供的AbstractExcelView或AbstractJExcelView即可。实现buildExcelDocument
 * 			方法，在方法中使用模型数据对象构建Excel文档就可以了。
 * 		- AbstractExcelView基于POI API，而AbstractJExcelView基于JExcelAPI。
 * 		- 视图对象需要配置IOC容器的一个Bean，使用BeanNameViewResolver作为视图解析器即可。
 * 		- 若希望直接在浏览器中直接下载Excel文档，则可以设置响应头Content-Disposition的值为：attachment;filename=xxx.xls即可。
 * 24.关于重定向
 * 		- 一般情况下，controller方法返回的String类型的值会被当做逻辑视图名来处理
 * 		- 如果返回的字符串带forward:或redirect:前缀时，springmvc会对它们做特殊处理：将forward:和redirect:当成指示符，其后的字符串
 * 			作为URL来处理：
 * 				- redirect:success.jsp ： 会完成一个到success.jsp的重定向操作
 * 				- forward:success.jsp ： 会完成一个到success.jsp的转发操作
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
			System.out.println("从数据库中获取一个User1对象：" + user1);
			//从数据库中获取一个user1对象放入map中，其实放在了implicitModel中了
			map.put("abc", user1);
		}
		return SUCCESS;
	}
	
	/**
	 * @ModelAttribute运行流程
	 * 	1.执行@ModelAttribute修饰的方法：从数据库取出user1对象，并放入map中，key为user1
	 * 	2.springmvc从map中取出user1对象，把表单的请求参数赋给该user1对象的各个属性
	 * 	3.springmvc把上述对象传入目标方法的参数
	 */
	@RequestMapping(	value="/**/user1/update", method=RequestMethod.POST )
		public String m13(User1 user1) throws IOException {
		System.out.println("修改user1：" + user1);
		return SUCCESS;
	}
	
	@RequestMapping(	value="/**/user1/modelattribute", method=RequestMethod.POST )
		public String m14(@ModelAttribute("abc") User1 user1) throws IOException {
		System.out.println("user1 @modelattribute：" + user1);
		return SUCCESS;
	}
	
	//自定义视图
	@RequestMapping(	value="/**/helloView", method=RequestMethod.POST )
		public String m15() throws IOException {
		System.out.println("helloView");
		return "helloView";
	}	
	
	//重定向
	@RequestMapping(	value="/**/redirect", method=RequestMethod.POST )
		public String m16() throws IOException {
		System.out.println("redirect");
		//return "redirect:/index.jsp";
		//return "redirect:/c01/helloView";
		return "forward:/c01/helloView";
	}		
	
	
	
	
}
