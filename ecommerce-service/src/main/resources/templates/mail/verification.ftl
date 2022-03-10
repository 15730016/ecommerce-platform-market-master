<#import "layout/defaultLayout.ftl" as layout>
<@layout.myLayout>
    <div>
        <table align="center" border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
            <tr>
                <td style="padding: 0px 30px 0px 30px;">
                    <p>Kính gửi ${name},</p>
                    <p>${msg}</p>
                    <p>
                        <a href="${href}">Xác thực tài khoản tại đây</a>
                    </p>
                </td>
            </tr>
            <tr>
                <td style="padding-left: 30px;">
                    <p>
                        Thân ái!!!, <br/> <em>SINT Kit</em>
                    </p>
                </td>
            </tr>
        </table>
    </div>
</@layout.myLayout>
