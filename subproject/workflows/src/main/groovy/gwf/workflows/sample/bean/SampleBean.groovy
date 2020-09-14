package gwf.workflows.sample.bean

import javax.ejb.Stateless

@Stateless
class SampleBean implements SampleLocal {

    @Override
    String sayHello() {
        return "SampleBean says hello."
    }
}
