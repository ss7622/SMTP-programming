package hello.core.exam06;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

public class SendMail{
    public static void SdMail(String smtpServer,String sender,String recipient, String content,String subject) throws Exception {
        Socket socket = new Socket(smtpServer,587);

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
        System.out.println("서버에 연결되었습니다.");

        String line = br.readLine();
        System.out.println("응답 = " + line);
        if(!line.startsWith("220")){
            throw new Exception("not smtp server");
        }

        System.out.println("ehlo 명령을 전송합니다.");
        pw.println("HELO mydomain.name");
        line = br.readLine();
        System.out.println("응답 = " + line);
        if(!line.startsWith("250")){
            throw new Exception("Helo 실패했습니다" + line);
        }


        System.out.println("STARTTLS 명령어를 전송합니다.");
        pw.println("STARTTLS");
        line = br.readLine();
        System.out.println("응답 = " + line);
        if(!line.startsWith("220")){
            throw new Exception("STARTTLS 실패했습니다" + line);
        }

        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) factory.createSocket(socket,socket.getInetAddress().getHostName(), socket.getPort(), true);
        pw = new PrintWriter(sslSocket.getOutputStream(),true);
        br = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));

        System.out.println("로그인 명령을 전송합니다.");
        pw.println("AUTH LOGIN");
        line = br.readLine();
        System.out.println("응답 = " + lin호".getBytes());
                pw.println(encode);

                line = br.readLine();
                System.out.println("응답 = " + line);
                if(line.startsWith("235")){
                    System.out.println("로그인 성공");
                }
                else{
                    System.out.println("로그인 실패");
                }
            }
        }

        System.out.println("MAIL FROM 명령을 전송합니다.");
        pw.println("MAIL FROM: "+sender);
        line = br.readLine();
        System.out.println("응답 = " + line);
        if(!line.startsWith("250")){
            throw new Exception("MAIL FROM 실패했습니다" + line);
        }

        System.out.println("RCPT 명령을 전송합니다");
        pw.println("RCPT TO: "+recipient);
        line = br.readLine();
        System.out.println("응답 = " + line);
        if(!line.startsWith("250")){
            throw new Exception("RCPT TO 실패했습니다" + line);
        }

        System.out.println("DATA 명령을 전송합니다");
        pw.println("DATA");
        line = br.readLine();
        System.out.println("응답 = " + line);
        if (!line.startsWith("354")) {
            throw new Exception("DATA 에서 실패했습니다:"+line);
        }

        System.out.println("본문을 전송합니다.");
        pw.println("From: " + sender );
        pw.println("To: " + recipient);
        pw.println("Subject: " + subject);
        pw.println("\n" + content );
        pw.println(".");
        line=br.readLine();
        System.out.println("응답:"+line);
        if (!line.startsWith("250")){
            throw new Exception("내용전송에서 실패했습니다:"+line);
        }

        System.out.println("접속 종료합니다.");
        pw.println("quit");


        pw.close();

    }
}
