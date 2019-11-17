<#import 'language.ftl' as Language>
<footer>
    <div class="footer">
        <div class="container">
            <div class="row">
                <div class="hidden-xs col-sm-4 col-md-4">
                    <p class="hidden-xs footer-title">
                        ${Language.lans[lan].footer_contact}
                    </p>
                    <p class="footer-content">${Language.lans[lan].footer_zip}</p>
                    <p class="footer-content">${Language.lans[lan].footer_tel}</p>
                    <p class="footer-content">${Language.lans[lan].footer_fax}</p>
                    <p class="footer-content">${Language.lans[lan].footer_mail}</p>
                    <p class="footer-content">${Language.lans[lan].footer_address}</p>
                </div>
                <div class="col-xs-4 col-sm-4 col-md-4">
                    <p class="hidden-xs footer-title">
                        ${Language.lans[lan].footer_aboutus}
                    </p>
                    <p class="footer-content"><a
                                href="${Language.lans[lan].url_path}offers.html">${Language.lans[lan].footer_offers}</a>
                    </p>
                    <p class="footer-content"><a
                                href="${Language.lans[lan].url_path}profile.html">${Language.lans[lan].footer_aboutus}</a>
                    </p>
                    <p class="footer-content"><a
                                href="${Language.lans[lan].url_path}issue.html">${Language.lans[lan].footer_suggest}</a>
                    </p>
                </div>
                <div class="col-xs-5 col-xs-offset-3 col-sm-4 col-sm-offset-0 col-md-4 col-md-offset-0 qr-container">
                    <p class="hidden-xs footer-title">
                        ${Language.lans[lan].footer_follow}
                    </p>
                    <img class="footer-qr ${(lan?? && lan == "en")?string("footer-qr-en", "")}"
                         src="http://img.bnibt.com/static/qrcode_for_gh_37878decb833_258.jpg"
                         alt="">
                    <p class="footer-content">${Language.lans[lan].footer_official}</p>
                </div>
            </div>
        </div>
    </div>
    <div class="copyright">
        <#if lan?? && lan == "en">
            <p>Copyright：北京北方生物技术研究所 CopyRight ©️2018 <span class="hidden-xs">京公网备2398928392</span></p>
            <p class="visible-xs-block">京公网备2398928392</p>
        <#else>
            <p>版权所有：北京北方生物技术研究所 CopyRight ©️2018 <span class="hidden-xs">京公网备2398928392</span></p>
            <p class="visible-xs-block">京公网备2398928392</p>
        </#if>
    </div>
</footer>