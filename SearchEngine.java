import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> list = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Ivan's Search Engine");
        } else if (url.getPath().equals("/showtext")) {
            return String.format("Hello World");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                list.add(parameters[1]);
                return String.format("List added %s!", parameters[1], list);
            }
            else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                String[] tempArr = new String[100];
                for(int i = 0; i < list.size(); i++) {
                    if(list.get(i).contains(parameters[1])) {
                        tempArr[i] = list.get(i);
                    }
                }
                System.out.print(tempArr);
            }

            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

