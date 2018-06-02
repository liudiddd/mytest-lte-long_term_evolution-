<html>
<body>
<h2>Hello World!</h2>
<a href="/helloworld">helloworld</a> <br>
<a href="/c01/m1">/c01/m1</a><br>
<form action="/c01/m1" method="post">
	id:<input type="text" name="id" value="1"/>
	name:<input type="text" name="name" value="Jack"/>
	<input type="submit" value="post to /c01/m1" />
</form><br>

<form action="/c01/m2/2/jackson" method="post">
	<input type="submit" value="post to /c01/m2/2/jackson" />
</form><br>

<!-- REST -->
<form action="/c01/rest/1" method="get">
	<input type="submit" value="rest get" />
</form><br>

<form action="/c01/rest" method="post">
	<input type="submit" value="rest post" />
</form><br>

<form action="/c01/rest/1" method="post">
	<input type="hidden" name="_method" value="DELETE"/>
	<input type="submit" value="rest delete" />
</form><br>

<form action="/c01/rest/1" method="post">
	<input type="hidden" name="_method" value="PUT"/>
	<input type="submit" value="rest put" />
</form><br>

<form action="/c01/param" method="post">
	<input type="text" name="id" value="3"/>
	<input type="submit" value="param" />
</form><br>

<form action="/c01/cookievalue" method="post">
	<input type="submit" value="cookievalue" />
</form><br>

<form action="/c01/pojo" method="post">
	name<input type="text" name="name" value="poppy" /><br>
	password<input type="text" name="password" value="123456" /><br>
	email<input type="text" name="email" value="12345678@qq.com" /><br>
	city<input type="text" name="address.city" value="beijing" /><br>
	province<input type="text" name="address.province" value="beijing" /><br>
	<input type="submit" value="pojo" />
</form><br>

<form action="/c01/servletapi" method="post">
	<input type="submit" value="servletapi" />
</form><br>

<form action="/c01/modelandview" method="post">
	<input type="submit" value="modelandview" />
</form><br>

<form action="/c01/modelandview01" method="post">
	<input type="submit" value="modelandview01" />
</form><br>

<form action="/c01/sessionattributes" method="post">
	<input type="submit" value="sessionattributes" />
</form><br>

<!-- 对用户User1进行修改操作，要求password字段不能修改 -->
<form action="/c01/user1/update" method="post">
	id<input type="hidden" name="id" value="1" /><br>
	name<input type="text" name="name" value="poppy" /><br>
	<!-- password<input type="text" name="password" value="123456" /><br> -->
	email<input type="text" name="email" value="12345678@qq.com" /><br>
	age<input type="text" name="age" value="12" /><br>
	<input type="submit" value="pojo" />
</form><br>

<form action="/c01/user1/modelattribute" method="post">
	<input type="hidden" name="id" value="1" /><br>
	<input type="submit" value="sessionattributes" />
</form><br>

<form action="/c01/helloView" method="get">
	<input type="submit" value="helloView" />
</form><br>

<form action="/c01/redirect" method="post">
	<input type="submit" value="redirect" />
</form><br>

<form action="/emps/dept/1" method="get">
	<input type="submit" value="get /emps dept 1" />
</form><br>

<form action="/emps" method="get">
	<input type="submit" value="emps" />
</form><br>




</body>
</html>
