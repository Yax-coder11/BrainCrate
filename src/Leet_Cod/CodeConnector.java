package Leet_Cod;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject; // Add this line

import static Leet_Cod.DisplayQuestion.cleanedCode;
import static Leet_Cod.DisplayQuestion.timeTaken;

public class CodeConnector {

    private static JTextArea codeArea;
    private static JTextArea outputArea;
    private static JButton submitBtn;
    String code;
    static  String stdout;


    CodeConnector () throws IOException {

        // Setup connection
        URL url = new URL("https://judge0-ce.p.rapidapi.com/submissions?base64_encoded=false&wait=true");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("X-RapidAPI-Key", "e194997bbcmsh1bb06a7f3c17a8bp14bb7fjsn4de844570d68");
        conn.setRequestProperty("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com");
        conn.setDoOutput(true);

        String code2 = cleanedCode.replaceAll("\t+", " ");
        String code = code2.replaceAll("\t+", "\n");
        System.out.println(code);

            // Prepare JSON
       String jsonInputString = "{"
                + "\"language_id\": 62,"
                + "\"source_code\": " + "\"" + code.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n") + "\""
                + "}";

       try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        catch (Exception ex) {
            // outputArea.setText("Error:\n" + ex.getMessage());
            ex.printStackTrace();
        }
        Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }

            // ✅ Parse JSON
          JSONObject json = new JSONObject(response.toString());


            stdout = json.optString("stdout", "No Output");

        String status = json.getJSONObject("status").optString("description", "Unknown");
        String compileOutput = json.optString("compile_output", "");
        String stderr = json.optString("stderr", "");
        String stdout = json.optString("stdout", "");

        StringBuilder formattedOutput = new StringBuilder();
        formattedOutput.append("Status: ").append(status).append("\n");



        if ("Compilation Error".equalsIgnoreCase(status)) {
            formattedOutput.append("Compilation Error:\n").append(compileOutput).append("\n");
      } else if ("Runtime Error".equalsIgnoreCase(status)) {
            formattedOutput.append("Runtime Error:\n").append(stderr).append("\n");
        } else if ("Accepted".equalsIgnoreCase(status)) {
            formattedOutput.append("Output:\n").append(stdout).append("\n");
        } else {
            formattedOutput.append("Other Info:\n").append(stdout).append("\n");
        }

        JOptionPane.showMessageDialog(null, formattedOutput.toString(), "Result", JOptionPane.INFORMATION_MESSAGE);

       new checkAnswerAddPoints(stdout,timeTaken);
        }
    }

    /*
    judge0api post format
    {
            "source_code": "#include <stdio.h>\nint main() { int a; scanf(\"%d\", &a); printf(\"%d\\n\", a*2); return 0; }",
            "language_id": 50,
            "stdin": "21",
            "expected_output": "42\n",
            "cpu_time_limit": "2",
            "memory_limit": "1024"
            }*/
