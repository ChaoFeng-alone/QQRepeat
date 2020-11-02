import bean.Friend;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        Properties properties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("config.properties"));
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String A = (String) properties.get("A");
        String B = (String) properties.get("B");

        List aList = null;
        List bList = null;
        List cList = new ArrayList<String>();

        FileInputStream fileA = null;
        FileInputStream fileB = null;

        String strA = null;
        String strB = null;

        try {
            fileA = new FileInputStream(new File("A.txt"));
            fileB = new FileInputStream(new File("B.txt"));

            byte[] tmp = new byte[fileA.available()];
            fileA.read(tmp);
            strA = new String(tmp);

            tmp = new byte[fileB.available()];
            fileB.read(tmp);
            strB = new String(tmp);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (A.equals("1")) {
            aList = new ArrayList<Friend>();
            SearchFriend(aList,strA);
        } else if (A.equals("2")) {
            aList = new ArrayList<String>();
            SearchPerson(aList,strA);
        }

        if (B.equals("1")) {
            bList = new ArrayList<Friend>();
            SearchFriend(bList,strB);
        } else if (B.equals("2")) {
            bList = new ArrayList<String>();
            SearchPerson(bList,strB);
        }

        //求交集
        if(A.equals("2")&&B.equals("2")){
           aList.retainAll(bList);
           cList=aList;
        }
        else if(!A.equals(B)){
            if(A.equals("1")){
                int count = aList.size();
                for (int i = 0; i < count; i++) {
                    cList.add(((Friend)aList.get(i)).getNum());
                }

                cList.retainAll(bList);
            }
            else if(B.equals("1")){
                int count = bList.size();
                for (int i = 0; i < count; i++) {
                    cList.add(((Friend)bList.get(i)).getNum());
                }

                cList.retainAll(aList);
            }
        }else{
            int count = aList.size();
            for (int i = 0; i < count; i++) {
                cList.add(((Friend)aList.get(i)).getNum());
            }

            List<String> dList = new ArrayList<>();
            int countb = bList.size();
            for (int i = 0; i < countb; i++) {
                dList.add(((Friend)bList.get(i)).getNum());
            }

            cList.retainAll(dList);
        }

        try {
            if(A.equals("2")&&B.equals("2")){
                WriteInNoPerson(cList);
            }
            else if(A.equals("1")){
                WriteInOnePerson(cList,aList);
            }else{
                WriteInOnePerson(cList,bList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void SearchFriend(List list, String in) {
        Pattern pattern = Pattern.compile("\"uin\":[0-9]{6,11},\r\n" +
                "\"groupid\":[0-9]{1,2},\r\n" +
                "\"name\":\".*\",\r\n" +
                "\"remark\":\".*\",");
        Matcher matcher = pattern.matcher(in);

        String str = null;
        String num;
        String name;
        String remark;
        while (matcher.find()) {
            str = matcher.group();
            Pattern patternName = Pattern.compile("\"name\":\".*\"");
            Pattern patternRemark = Pattern.compile("\"remark\":\".*\"");
            Pattern patternNum = Pattern.compile("[0-9]{6,11}");

            Matcher matcherName = patternName.matcher(str);
            Matcher matcherRemark = patternRemark.matcher(str);
            Matcher matcherNum = patternNum.matcher(str);

            matcherName.find();
            matcherNum.find();
            matcherRemark.find();

            num = matcherNum.group();
            name = matcherName.group();
            name = name.substring(8, name.length() - 1);
            remark = matcherRemark.group();
            remark = remark.substring(10, remark.length() - 1);

            list.add(new Friend(num, name, remark));
        }
    }

    private static void SearchPerson(List list,String str){
        Pattern pattern = Pattern.compile("[0-9]{6,11}");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()){
            list.add(matcher.group());
        }
    }

    private  static void WriteInOnePerson(List a,List b) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("c.txt"));
        for (int i = 0; i < a.size(); i++) {
            String tmp = (String) a.get(i);
            Friend tfriend;
            for (int j = 0; j < b.size(); j++) {
                tfriend=(Friend)b.get(j);
                if(tfriend.getNum().equals(tmp)){
                    String str = tfriend.getNum()+"\t"+tfriend.getName()+"\t"+tfriend.getRemark()+"\n";
                    fos.write(str.getBytes());
                    break;
                }
            }
        }
        fos.close();
    }

    private static void WriteInNoPerson(List a) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("c.txt"));
        for (int i = 0; i < a.size(); i++) {
            String tmp = (String) a.get(i)+"\n";
            fos.write(tmp.getBytes());
        }
        fos.close();
    }
}
