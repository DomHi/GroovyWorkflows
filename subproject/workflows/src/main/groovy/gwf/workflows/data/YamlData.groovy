package gwf.workflows.data;

class YamlData {

    String name;

    String[] values;

    @Override
    String toString() {
        return "YamlData{" +
                "name='" + name + '\'' +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}
