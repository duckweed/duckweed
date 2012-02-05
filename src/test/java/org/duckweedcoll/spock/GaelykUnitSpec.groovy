package org.duckweedcoll.spock

import com.google.appengine.api.LifecycleManager
import com.google.appengine.api.NamespaceManager
import com.google.appengine.api.backends.BackendService
import com.google.appengine.api.blobstore.BlobstoreServiceFactory
import com.google.appengine.api.capabilities.CapabilitiesService
import com.google.appengine.api.channel.ChannelService
import com.google.appengine.api.datastore.DatastoreServiceFactory
import com.google.appengine.api.files.FileServiceFactory
import com.google.appengine.api.mail.MailServiceFactory
import com.google.appengine.api.memcache.MemcacheServiceFactory
import com.google.appengine.api.oauth.OAuthService
import com.google.appengine.api.taskqueue.QueueFactory
import com.google.appengine.api.urlfetch.URLFetchService
import com.google.appengine.api.users.UserServiceFactory
import com.google.appengine.api.utils.SystemProperty
import com.google.appengine.api.xmpp.XMPPServiceFactory
import groovyx.gaelyk.GaelykCategory
import groovyx.gaelyk.ImagesServiceWrapper
import groovyx.gaelyk.QueueAccessor
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse
import com.google.appengine.tools.development.testing.*

class GaelykUnitSpec extends spock.lang.Specification {
	
	def groovletInstance
	def helper
	def sout, out, response
	def datastore, memcache, mail, urlFetch, images, users, user
	def defaultQueue, queues, xmpp, blobstore, files, oauth, channel
	def namespace, localMode, app, capabilities, backends, lifecycle
	
	def setup(){
		//system properties to be set
		SystemProperty.environment.set("Development")
		SystemProperty.version.set("0.1")
		SystemProperty.applicationId.set("1234")
		SystemProperty.applicationVersion.set("1.0")
	
		helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig(),
			new LocalMailServiceTestConfig(),
			new LocalImagesServiceTestConfig(),
			new LocalUserServiceTestConfig(),
			new LocalTaskQueueTestConfig(),
			new LocalXMPPServiceTestConfig(),
			new LocalBlobstoreServiceTestConfig(),
			new LocalFileServiceTestConfig()
		)
		helper.setUp()
		
		Object.mixin GaelykCategory
		
		sout = Mock(ServletOutputStream)
		out = Mock(PrintWriter)
		response = Mock(HttpServletResponse)
		oauth = Mock(OAuthService)
		channel = Mock(ChannelService)
		urlFetch = Mock(URLFetchService)
		capabilities = Mock(CapabilitiesService)
		backends = Mock(BackendService)
		
		datastore = DatastoreServiceFactory.datastoreService
		memcache = MemcacheServiceFactory.memcacheService
		mail = MailServiceFactory.mailService
		images = ImagesServiceWrapper.instance
		users = UserServiceFactory.userService
		user = users.currentUser
		defaultQueue = QueueFactory.defaultQueue
		queues = new QueueAccessor()
		xmpp = XMPPServiceFactory.XMPPService
		blobstore = BlobstoreServiceFactory.blobstoreService
		files = FileServiceFactory.fileService
		lifecycle = LifecycleManager.instance

		namespace = NamespaceManager
		localMode = (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development)
		
		app = [
			env: [
				name: SystemProperty.environment.value(),
				version: SystemProperty.version.get(),
			],
			gaelyk: [
				version: '0.7'
			],
			id: SystemProperty.applicationId.get(),
			version: SystemProperty.applicationVersion.get()
		]

	}
	
	def teardown(){
		helper.tearDown()
	}
		
	def groovlet = {
        String script ->
		groovletInstance = new GroovletUnderSpec(script)
		
		[ 'sout', 'out', 'response', 'datastore', 'memcache', 'mail', 'urlFetch', 'images', 'users', 'user', 'defaultQueue', 'queues', 'xmpp', 
		  'blobstore', 'files', 'oauth', 'channel', 'capabilities', 'namespace', 'localMode', 'app', 'backends', 'lifecycle'
		].each { groovletInstance."$it" = this."$it" }

        def groovletname = "${script.tokenize('.').first().tokenize('/').last()}"

        final firstChar = groovletname.charAt(0)
        if(firstChar.isUpperCase()){
            groovletname = firstChar.toLowerCase().toString() + groovletname.substring(1)
        }

        this.metaClass."$groovletname" = groovletInstance
	}
		
}
	
