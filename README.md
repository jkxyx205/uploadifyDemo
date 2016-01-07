#uploadify Demo

###Flash mode

```javascript
  $('#file_upload').uploadify({
            'buttonText':'文件上传',
            //'buttonClass':'some-class',
            /*'buttonImage':'uploadify/btn.png',*/
            'queueID':'queue',
            'removeCompleted':false,
            'onUploadStart' : function(file) {
                //alert('Starting to upload ' + file.name);
                show();
            },
            'formData'     : {
                'timestamp' : timestamp,
                'username' :'rick.xu'
                //'token'     : '<?php echo md5('unique_salt' . $timestamp);?>'
            },
            'swf'      : 'uploadify/uploadify.swf',
            'uploader' : 'http://xhope.top/upload'
        });
```        
###Html5 mode
```javascript
$('#file_upload').uploadifive({
            'auto'             : true,
            'buttonText':'文件上传',
            removeCompleted:false,
            //'buttonClass':'some-class',
            'buttonImage':'uploadify/btn.png',
            /*'checkScript'      : 'check-exists.php',*/
           /* 'formData'         : {
                'timestamp' : '<?php echo $timestamp;?>',
                'token'     : '<?php echo md5('unique_salt' . $timestamp);?>'
            },*/
            'queueID'          : 'queue',
            'uploadScript'     : 'http://xhope.top/upload', //cross domain visit
            'onSelect':function() {show();},
            'onUploadComplete' : function(file, data) { console.log(data); }
        });
```
###Make the sever crosss domain visited

a. `crossdomain.xml`
```xml
<cross-domain-policy>
    <site-control permitted-cross-domain-policies="all" />
    <allow-access-from domain="*" />
    <allow-http-request-headers-from domain="*" headers="*"/>
</cross-domain-policy>
```
b. `web.xml`
```xml
    <filter>
        <filter-name>CORS</filter-name>
        <filter-class>com.thetransactioncompany.cors.CORSFilter</filter-class>
        <init-param>
            <param-name>cors.allowOrigin</param-name>
            <param-value>*</param-value>
        </init-param>
        <init-param>
            <param-name>cors.supportedMethods</param-name>
            <param-value>GET, POST, HEAD, PUT, DELETE</param-value>
        </init-param>
        <init-param>
            <param-name>cors.supportedHeaders</param-name>
            <param-value>Accept, Origin, X-Requested-With, Content-Type, Last-Modified</param-value>
        </init-param>
        <init-param>
            <param-name>cors.exposedHeaders</param-name>
            <param-value>Set-Cookie</param-value>
        </init-param>
        <init-param>
            <param-name>cors.supportsCredentials</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CORS</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
  ```
  c. `Download conresponding jars`
  
  cors-filter-2.4.jar
  
  java-property-utils-1.9.1.jar
