package JPD.winnowing;

/*
 此一物件目的在過濾空白及不可見字元
 */
public class StringFilter {

    public StringFilter() {
    }

    /*
     去除空白及不可見字元
     */
    public String trim(String data) {
        int len;
        char ch;
        if (data == null) {
            return "";
        }
        String rtnStr = "", tempStr;
        tempStr = data.toLowerCase();
        len = tempStr.length();

        for (int i = 0; i < len; i++) {
            ch = tempStr.charAt(i);
            /* if (ch >= 97 && ch <= 122){
                 rtnStr += tempStr.subSequence(i,i+1);
             }*/
            if (ch >= 33 && ch <= 126) {
                rtnStr += tempStr.substring(i, i + 1);
            }
        }
       // System.out.println(rtnStr);
        return rtnStr;
    }
}
