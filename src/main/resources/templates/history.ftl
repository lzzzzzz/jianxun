<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import "common/language.ftl" as Language>
<#assign historyList={
"zh": [
{
"title": "1985年7月1日  公司成立",
"content": "成立中国同位素公司北方免疫试剂研究所，集研发、生产和营销为一体的专业化体外诊断试剂全资国营企业。成立第一年即实现盈利。",
"other": "（八十年代初，在中央改革开放政策的引导下，核工业部的经济体制和发展方向也在不断进行调整。逐步由单一的军工产品向着“以核为主，多种经营”转变，有计划，有步骤地向市场开放。1985年，中国同位素公司充分利用这一契机在核工业部的领导下建立了北京北方免疫试剂研究所，成为一家集研发、生产和营销为一体的专业化体外诊断试剂全资国营企业。）"
},
{
"title": "1990年",
"content": "T3放免试剂盒获得全医药卫生科技成果展览会银质奖章，T4放免试剂盒获部优产品奖。"
},
{
"title": "1994年7月  企业合并更名、建成实验楼并投入使用",
"content": "1994年，与北京华清生物技术研究所合并，并更名为北京北方生物技术研究所（以下简称北方所）。同年3500平方米的新实验楼竣工，具备了年产10万盒的生产线。",
"other": "（建成并启用3500平方米的集中空调实验楼，这是一座符合GMP要求的全封闭实验楼，它的建成标志着北方所又向更高的标准迈进。实验楼内配置了国内外先进的仪器设备，如：高压液相色谱、低温高速离心机，倒置显微镜等。硬件的逐步完善，同时也要跟上软件的管理。通过进行ISO-9000认证，北方所形成了合理的质量管理体系，为产品质量提供了更好的保障。）"
},
{
"title": "1999年",
"content": "1999.12 通过了ISO9000国际质量管理休系认证。同年，北方所开展了进出口业务，产品出口韩国。"
},
{
"title": "2003年",
"content": "2003年顺利通过ISO9000国际质量管理休系认证2000版的换版工作"
},
{
"title": "2004年",
"content": "2004.7 北方所时间分辨、化学发光免疫分析测定盒获国家重点新产品证书。<br/>2004.9 一次性通过GMP认证，产品质量得到了国际同行的认可，产品具有优良的质量保证。"
},
{
"title": "2005年",
"content": "2005.12 承担并完成原国家科工委核能开发“十 • 五”项目——肝纤维四项放射免疫分析药盒。"
},
{
"title": "2007年",
"content": "北方所建立起符合ISO13485和《体外诊断试剂生产实施细则》要求的医疗器械体系，并多次通过体系考核。"
},
{
"title": "2008年8月",
"content": "时间分辨、化学发光产品获国家药监局40多个批准文号，北方所时间分辨、化学发光产品平台建立。"
},
{
"title": "2010年6月",
"content": "国家核安全局批准北方所定量放免药盒放射性物质的运输豁免，成为唯一一家放免药盒运输豁免企业。"
},
{
"title": "2010年11月",
"content": "《肝纤维化血清标志物放免分析药盒的研发》获核工业集团公司三等奖；核能行业协会科学技术三等奖。"
},
{
"title": "2011年",
"content": "甲状腺功能等4个系列化学发光免疫检测试剂盒的研发获得核工业集团公司2011年度科技进步三等奖。"
},
{
"title": "2012年",
"content": "肝纤维等19种单克隆抗体研制及应用获得中核集团科技进步三等奖。"
},
{
"title": "2013年",
"content": "承担了国家“十一 • 五”核能开发科研技术研制项目子专题放射免疫分析药盒的研制工作并取得进展。<br/>五项心血管系列化学发光项目获核工业集团公司青年创新团队科研专项资金支持。<br/>2013.12 牵头提出I-125体外放射免疫试剂使用豁免管理，获国家环保部批准，为体外诊断行业发展起到积极推动作用。"
},
{
"title": "2014年8月  企业更名",
"content": "2014年8月企业类型转变为有限责任公司，注册资本由1000万增加至1800万"
},
{
"title": "2015年  产品出口孟加拉",
"content": "2015年2月承接原子高科体外诊断产品，出口孟加拉，丰富进出口业务"
},
{
"title": "2018年7月  整体上市，营业收入新突破",
"content": "2018年7月随中国同辐整体上市，营业收入突破1亿元"
},
{
"title": "2018年10月  企业增资增项",
"content": "2018年10月完成北方所增资增项工作，企业注册资本由1800万变增加至8600万。"
},
{
"title": "2018年11月  成功收购子公司",
"content": "2018年北方所成功收购一家子公司——上海优晶生物科技有限公司，丰富了北方所的进出口业务，初步实现了同辐“做大”的战略目标。"
},
{
"title": "2018年11月  中国同辐体外诊断技术研发中心挂牌",
"content": "于2018年11月23日揭牌，依托北方所成熟的免疫诊断（放免、酶免、时分、发光和胶体金技术）和生物原材料研究开发技术平台（抗原/抗体）建立，成为中国同辐股份有限公司下属中国同辐研究院“1+N”管理模式下第一个研发中心。联合国内外优势企业和研究机构，逐步形成覆盖体外诊断全领域的科技研发中心。"
}
],
"en": [
{
"title": "Establishment of the Enterprise on July 1, 1985",
"content": "Establish China Isotope Corporation---- Northern Institute of Immune Reagents which is a state enterprise including research、production and marketing. Profit had be realized in the first year of its establishment."
},
{
"title": "July in 1994  Build and use the laboratory building",
"content": "In 1994, it was renamed Beijing North Institute of Biotechnology Co,.Ltd (BNIBT). In the same year, 3500 square meters of the new experimental building was completed which can provide a production line of 100,000 kits."
},
{
"title": "In 1999",
"content": "In December 1999, it passed ISO9000 international quality control system certification. In the same year, BNIBT carried out import and export business and the products were exported to Korea."
},
{
"title": "In 2004",
"content": "In September 2004, the products passed GMP certification. The quality of the products has been recognized by the international counterparts."
},
{
"title": "August in 2008",
"content": "TRFIA、CLIA products had been approved by more than 40 license numbers of the State Drug Administration and two platforms were established."
},
{
"title": "In 2015  Products were exported to Bangladesh",
"content": "Exported to Bangladesh in February 2015 which enriched import and export business."
},
{
"title": "Purchase the subsidiaries successfully on November 2018",
"content": "In 2018, BNIBT successfully acquired a company ——Shanghai Youjing Biological Technology Co., Ltd."
},
{
"title": "In November 2018",
"content": "The vitro diagnostic technology research and Development Center of China Isotope & Radiation Corporation was established.",
"other": "Relying on the mature immune diagnosis (RIA, ELISA, TFRIA, CLIA and CGIA) and platform of research biological raw materials, BNIBT has become the first R&D center under the \"1+N\" management mode of China Isotope & Radiation Corporation. BNIBT hase joined with excellent enterprises/research institutes at home and abroad , gradually forming a research and development center which covers the whole field of vitro diagnosis."
}
]
}>
<@base.base_container
title="${Language.lans[lan].history_title}"
headers=[
'/css/swiper.css',
"/css/breadcrumb.css",
"/css/history.css"
]
scripts=[
'/js/swiper.min.js',
"/js/history.js"
]>
    <div class="history">
        <img class="banner hidden-xs" src="http://img.bnibt.com/static/banner_sevice.png" alt="">
        <img class="banner visible-xs-block"
             src="http://img.bnibt.com/static/banner_sevice.png?x-oss-process=image/crop,w_750,h_300,g_center" alt="">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "#",
        "zh": "关于我们",
        "en": "Advisory"
        }, {
        "url": "${Language.lans[lan].url_path}history.html",
        "zh": "发展历程",
        "en": "History"
        }] />
        <div class="container">
            <h1 class="history-title">
                ${Language.lans[lan].history_content_desc}
            </h1>
        </div>
        <div class="history-infor">
            <div class="container">
                <div class="history-china">
                    <img src="http://img.bnibt.com/static/map_china.png" alt="">
                </div>
                <#if lan?? && lan == "en">
                    <div class="history-contact">
                        <div class="contact-item">
                            <p class="contact-title">
                                Domestic
                            </p>
                            <p class="contact-infor">
                                Contact：
                            </p>
                            <p class="contact-infor">
                                Marketing Department：Tel: 010-87504050
                            </p>
                        </div>
                        <div class="contact-item">
                            <p class="contact-title">
                                Foreign
                            </p>
                            <p class="contact-infor">
                                Contact：
                            </p>
                            <p class="contact-infor">
                                Marketing Department：Tel: 010-87504050，87504014-723
                            </p>
                        </div>
                    </div>
                <#else>
                    <div class="history-contact">
                        <div class="contact-item">
                            <p class="contact-title">
                                国内
                            </p>
                            <p class="contact-infor">
                                联系方式：
                            </p>
                            <p class="contact-infor">
                                营销部：电话 010-87504050
                            </p>
                        </div>
                        <div class="contact-item">
                            <p class="contact-title">
                                国外
                            </p>
                            <p class="contact-infor">
                                联系方式：
                            </p>
                            <p class="contact-infor">
                                营销部：电话 010-87504050，87504014-723
                            </p>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
        <div class="timeline visible-xs-block">
            <#list historyList[lan] as history>
                <div class="timeline-item">
                    <div class="timeline-item-title">
                        <img src="http://img.bnibt.com/static/circle.png" alt="">
                        <p>${history.title}</p>
                    </div>
                    <div class="timeline-item-content">
                        <p>${history.content}</p>
                        <#if (history.other)??>
                            <p class="other">
                                ${history.other}
                            </p>
                        </#if>
                    </div>
                </div>
            </#list>

        </div>
        <div class="desktop-timeline hidden-xs">
            <div class="container">
                <div class="swiper-container">
                    <div class="swiper-pagination"></div>
                    <div class="swiper-wrapper">
                        <#list historyList[lan] as history>
                            <div class="swiper-slide">
                                <div class="desktop-timeline-item">
                                    <img src="http://img.bnibt.com/static/swiper-icon.png" alt="">
                                    <p class="title">${history.title}</p>
                                    <p>${history.content}</p>
                                    <p class="other">${(history.other)!''}</p>
                                </div>
                            </div>
                        </#list>

                    </div>

                </div>
            </div>
        </div>
    </div>
</@base.base_container>