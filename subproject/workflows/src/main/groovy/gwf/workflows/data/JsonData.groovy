package gwf.workflows.data;

class JsonData {

    String name;

    String[] values;

    @Override
    String toString() {
        return "JsonData{" +
                "name='" + name + '\'' +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}
