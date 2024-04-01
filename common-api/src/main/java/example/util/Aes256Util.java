package example.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class Aes256Util {   // AES 뒤에 붙은 숫자는 키의 길이 의ㅣ미 외부에 노출되어서는 안됨. 256비트(32바이트)
    public static String alg = "AES/CBC/PKCS5Padding";
    // AES 암호화(비밀키, 블록암호 운용방식, 패딩기법이있음)
    // CBC 암호화에 사용할 블록 암호 운용 모드(암호화하기 전에 이전 블록의 암호문을 현재 블록의 평문과 연결하여 암호화하는 방식)
    // PACKS5Padding 패딩기법(데이터를 특정크기로 맞추기위해 부족한부분의 공간을 의미없는 문자들로 채우는것)
    private static String KEY = "AQuizProgramIsKeyJustKey";  // 암호화 키
    private static String IV = KEY.substring(0,16); // IV 는 KEY의(0번째 인텍스 부터 16문자를 추출)

    /* 암호화 */
    public static String encrypt(String text){  // 암호화되지 않은 데이터를 암호화 시킴
        try{
            Cipher cipher = Cipher.getInstance(alg);    // Cipher cipher 는 (암호화, 복호화 기능을 제공) = 알고리즘 이름은 AES 고 모드는 CBC 모드 PKC5Padding 방식으로 지정
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(),"AES");  // SecretKeySpec 대칭키를 나타내는 객체 = "AES"를 사용하여 KEY를 바이트배열로 변환하여 대칭키 생성
            // 바이트배역은 주로 파일입출력, 네트워크 통신 등에서 사용됨 8비트 이진 데이터로 표현
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes());   // 백터 객체 생성
            // 첫번째 만드는 암호화 키는 이전이 없어서 벡터를 사용
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec); // cipher.객체 초기화(어떤용도인지, 암호화에 사용될 대칭키, ? )
            // ENCRYPT_MODE 암호화 모드로 설정
            // DECRYPT_MODE 복호화 모드로 설정
            // WRAP_MODE 키 또는 기타 보호되는 데이터를 암호화하여 외부로 전송하거나 저장하는 데 사용
            // UNWRAP_MODE 암호화된 키 또는 기타 보호되는 데이터를 복호화하여 다시 사용 가능한 형태로 복원하는 데 사용
            // doFinal()을 호출하여 암호화, 복호화.

            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));   // cipher 를 암호화한다(text.바이트배열로 변환(평문(암호화되지않은 원본데이터)을 UTF-8인코딩))
            // 암호화 작업은 바이트데이터로 처리되기 때문에 문자열을 바이트 배열로 변환한다.
            return Base64.encodeBase64String(encrypted);    // 암호화된 데이터를 Base64형식으로 인코딩하여 문자열로 반환한다(암호화된 데이터)

        }catch (Exception e){
            return null;    // 예외발생 시 null 반환
        }
    }

    /* 복호화 */
    public static String decrypt(String text){  // 암호화된 데이터를 평문 데이터로 복원
        try{
            Cipher cipher = Cipher.getInstance(alg);    // Cipher cipher 는 (암호화, 복호화 기능을 제공) = 알고리즘 이름은 AES 고 모드는 CBC 모드 PKC5Padding 방식으로 지정
            SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(),"AES");  // SecretKeySpec 대칭키를 나타내는 객체 = "AES"를 사용하여 KEY를 바이트배열로 변환하여 대칭키 생성
            // 바이트배역은 주로 파일입출력, 네트워크 통신 등에서 사용됨 8비트 이진 데이터로 표현
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)); // 여기에 백터를 왜 또 사용하지?
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivParameterSpec); // cipher.객체 초기화(어떤용도인지(복호화 모드), 암호화에 사용될 대칭키, ? )

            byte[] decryptedByte = Base64.decodeBase64(text);   // Base64 형식으로 인코딩된 암호문(파라미터로 들어온 text)을 디코딩하여 바이트 배열로 변환
            byte[] decrypted = cipher.doFinal(decryptedByte);   // 바이트 배열타입의 decrypted 는 cipher.결과반환()
            return new String(decrypted,StandardCharsets.UTF_8);    // 반환한다 String 타입(바이트 배열, UTF-8 문자 인코딩을 사용)

        }catch (Exception e){
            return null;
        }
    }
}
