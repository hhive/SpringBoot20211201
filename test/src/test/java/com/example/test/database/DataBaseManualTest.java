package com.example.test.database;

import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;

/**
 * TODO
 *
 * @author lih@yunrong.cn
 * @version V3.0
 * @since 2021/5/18 15:13
 */
public class DataBaseManualTest {

    public static void main(String[]  args) {
        new DataBaseManualTest().insert();
    }

    private Date now = new Date();

    public void insert() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            st = conn.createStatement();
            String sql =
                " insert into capital_repay_withhold_order_file (loan_no, tenant_id, loan_type, return_principal, return_principal_interest, return_interest, return_penalty_interest, debit_account, deduction_sub_account, process_result , remark, account_date, account_serial, process_status, create_time , update_time) "
                    + "values ('0000091005', '000', '0', '5073.33', '0.00' , '0.00', '1.52', '6214180200100445005', '000000', '0' , '${remark}', '2021-06-04', '04372164', '1', sysdate , sysdate)";

            String path = "C:\\Users\\jax\\Desktop\\ZXA125001O";
            RandomAccessFile randomAccessFile = new RandomAccessFile(path, "r");
            String line;
            while ((line = randomAccessFile.readLine()) != null) {
                System.out.println(line);
                String[] lines = line.split("#@\\|");
                System.out.println(Arrays.toString(lines));
                lines[9] = new String(lines[9].getBytes(StandardCharsets.ISO_8859_1), "gb2312");
                lines[9] = URLDecoder.decode(lines[9], StandardCharsets.UTF_8.name());

                sql = sql.replace("${remark}", lines[9]);
                break;
            }

            System.out.println(sql);
            int num = st.executeUpdate(sql);  //update
            if (num > 0) {
                System.out.println("插入成功！！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    public void delete() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "delete from users where id=4";
            st = conn.createStatement();
            int num = st.executeUpdate(sql);
            if (num > 0) {
                System.out.println("删除成功！！");
            }
        } catch (Exception e) {

        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    public void update() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "update users set name='wuwang',email='wuwang@sina.com' where id=3";
            st = conn.createStatement();
            int num = st.executeUpdate(sql);
            if (num > 0) {
                System.out.println("更新成功！！");
            }
        } catch (Exception e) {

        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    public void find() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select * from users where id=1";
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        } catch (Exception e) {

        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

}
