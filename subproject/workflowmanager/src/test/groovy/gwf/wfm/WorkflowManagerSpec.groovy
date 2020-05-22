package gwf.wfm

import spock.lang.Specification

class WorkflowManagerSpec extends Specification {

    def "ScriptManager says blubb"() {

        when:
        def greeting = new WorkflowManagerImpl().greeting

        then:
        greeting == 'blubb'
    }
}
