package gwf.wfm

import spock.lang.Specification

class WorkflowManagerSpec extends Specification {

    def "ScriptManager says blubb"() {

        when:
        def greeting = new WorkflowManager().greeting

        then:
        greeting == 'blubb'
    }
}
