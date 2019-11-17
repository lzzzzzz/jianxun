<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import "common/language.ftl" as Language>
<@base.base_container
title="${Language.lans[lan].cooperation_title}"
headers=[
"/css/breadcrumb.css",
"/css/cooperation.css"
]
scripts=[
"/js/cooperation.js"
]>
    <div class="cooperation">
        <img class="banner hidden-xs" src="http://img.bnibt.com/static/banner_sevice.png" alt="">
        <img class="banner visible-xs-block" src="http://img.bnibt.com/static/banner_sevice.png?x-oss-process=image/crop,w_750,h_300,g_center" alt="">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "${Language.lans[lan].url_path}cooperation.html",
        "zh": "招商合作",
        "en": "Cooperation"
        }] />
       <div class="container">
           <h1 class="cooperation-title">
               ${Language.lans[lan].cooperation_content_title}
           </h1>
           <#if lan?? && lan == "en">
               <div class="cooperation-content">
                   <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Beijing North Biotechnology Research Institute Co., Ltd. (BNIBT) was established in July 1985 and is affiliated to China National Nuclear Industry Group Co., Ltd. (China Tongfu, 1763.HK).It is one of the earliest high-tech enterprises engaged in the research, development, production and operation of in vitro diagnostic reagents in China. It is also the largest enterprise in the country that produces and exempts in vitro diagnostic kits. BNIBT sincerely invite agents from all over the world to cooperate and create the markets.</p>
                   <p style="padding-left: 30px;margin-top: 10px;">Address:Panjiamiao NO.A20 , Fengtai Distrct, Beijing, China</p>
                   <p style="padding-left: 30px;">Website: <a href="https://bnibt.en.alibaba.com/">https://bnibt.en.alibaba.com/</a></p>
                   <p style="padding-left: 30px;">HOT SALES PRODUCTS <a href="http://www.bnibt.com/">http://www.bnibt.com/</a> </p>
                   <p style="padding-left: 30px;"><a href="tel:010-87509221">Tel: 010-87509221</a></p>
               </div>
           <#else>
               <div>
                   <div class="cooperation-content">
                       <h2>
                           成为代理商
                       </h2>
                       <h3>一、要想成为北方所产品的代理商，一般需要满足：</h3>
                       <p>
                           公司规模在5人以上
                       </p>
                       <p>
                           拥有一定的销售资源，能完成一定的销售任务
                       </p>
                       <h3>二、成为代理商后，您将获得：</h3>
                       <p>
                           a. 最新的产品信息
                       </p>
                       <p>
                           b. 优惠的代理价格
                       </p>
                       <p>
                           c. 宣传资料提供
                       </p>
                       <p>
                           d. 促销礼品支持
                       </p>
                       <p>
                           e. 技术与销售培训
                       </p>
                       <p>
                           f. 促销礼品支持
                       </p>
                       <h3>三、北方所以声/市/自治区为单位设置负责营销的地区经理，您可以与负责本地区的经理联系。</h3>
                       <p>地区经理的联系方式请从联系方式或在线反馈模块获取。</p>
                       <p>您也可以直接联系市场部（010-87509221）。</p>
                       <h3>四、发展原则：</h3>
                       <p>北方所秉承与经销商“平等、互利、共赢”的宗旨在全国范围内发展经销商，以便更好地服务于用户，共同为诊断医疗事业服务。</p>
                   </div>
                   <div class="cooperation-content">
                       <h2>
                           成为经销商
                       </h2>

                       <h3>经销商是指具有独立的法人资格、专门的人员，并有专门的经费进行公司系列产品的宣传、推广、销售和服务的医疗器械经营公司。经销商应具备以下特点：在当地有一定的市场知名度，具有良好的社会声誉；具备敬业、守信的经营作风；根据公司的整体市场策划，能够独立开拓市场，并能够承担产品相应的培训及技术服务工作；结合当地具体情况，制定全年的销售、推广计划；承诺全年的销售量，并提交书面材料备案。</h3>
                       <h3>加盟资格:</h3>
                       <p>
                           a. 具有独立法人资格，具有相应等级的医疗器械、药品的经营资质；
                       </p>
                       <p>b. 拥有从事营销医疗器械、药品或相关产品的经验,具有良好的企业形象及医疗行业服务或产品的客户来源；</p>
                       <p>c. 具有相应规模的营业场所，足够资金、经营能力，经营正当,信誉良好；</p>
                       <p>d.备行业拓展能力，熟悉行业相关的特点、价格以及业务操作方式；熟悉医院采购决策程序；</p>
                       <p>e.拥有自身销售渠道及专职销售队伍；</p>
                       <p>f.对产品服务、支持做出一定承诺，并接受公司的监督、指导；</p>
                       <p>g.有志于推进医疗诊断系统及当地诊断事业的发展并对公司产品有强烈的推广信心和热情。</p>
                   </div>
               </div>
               <div class="product-agent">
                   <h2 class="cooperation-title">
                       产品代理与推广
                   </h2>
                   <p>北方所24年来，累积了深厚的客户基础，与北方所有过业务往来的用户数量达到了2千多家，医院客户数遍布全国各省市地区（西藏除外）。</p>
                   <p>北方所拥有多年积累的深厚的客户基础以及品牌效应，利用公司在各方面的比较优势，面向不同类型的客户，提供了多样化的产品、特色的服务以及实施了多项合作。</p>
                   <h3>欢迎您选择北方所作为您产品在中国地区的总代理与及推广服务商！请联系市场部010-87509221</h3>
               </div>
               <div class="export">
                   <h2 class="cooperation-title">
                       出口合作
                   </h2>
                   <div class="export-img">
                       <img src="http://img.bnibt.com/static/img_forigner.png" alt="">
                   </div>
                   <div class="export-infor">
                       <p>欢迎咨询我们</p>
                       <p>https://www.1688.com/</p>
                   </div>
               </div>
           </#if>

       </div>

    </div>
</@base.base_container>