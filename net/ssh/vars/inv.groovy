// Visit https://gradle-ssh-plugin.github.io/docs/

@Grab('ch.qos.logback:logback-classic:1.1.2')
@Grab("org.hidetake:groovy-ssh:2.10.1")
import org.hidetake.groovy.ssh.Ssh

inv {
    markdown '''
Provide a wrapper to Hidetake groovy-ssh implementation.
It creates a new service per callee.
'''

    broadcast { SSH } using {
        markdown '''
Returns a new SSH handler. 
'''
        delayed true
        global {
            def instance = [:]

            instance += [
                    ssh: Ssh.newService(),
                    debugMode: {
                        instance.ssh.runtime.logback(level: 'DEBUG')
                    }
            ]

            return instance
        }
    }
}

