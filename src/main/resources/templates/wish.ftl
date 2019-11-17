<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import "common/language.ftl" as Language>
<@base.base_container
title="我的心愿单-北方所"
headers=[
"/css/breadcrumb.css",
"/css/wish.css"
]
scripts=[
"/js/wish.js"
]>
    <div class="wish">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "${Language.lans[lan].url_path}wish.html",
        "zh": "我的心愿单",
        "en": "Wish"
        }] />
       <div class="container">
           <div class="wish-title">
               <h1>全部商品</h1>
               <a href="#" class="btn del-wish" role="button"  data-toggle="modal" data-target="#deleteAllModal">删除全部</a>
           </div>
           <div class="wish-list">
               <#if wishList?? && wishList?size gt 0>
                   <div class="wish-list-container">
                       <#list wishList as wish>
                           <div class="wish-item">
                               <div class="wish-item-container">
                                   <div class="wproduct-infor">
                                       <a class="infor-container" href="${Language.lans[lan].url_path}products/${wish.productId}.html">
                                           <div class="wproduct-banner">
                                               <img src="${(wish.product.bannerList[0].resourceUrl)!''}${(wish.product.bannerList[0].params)!''}" alt="">
                                           </div>
                                           <div class="wproduct-detail">
                                               <h2 class="wproduct-title">
                                                   ${(wish.product.title)!'无'}
                                               </h2>
                                               <p class="wproduct-other">型号：${(wish.product.modelName)!'无'}</p>
                                               <p class="wproduct-other">规格：${(wish.product.specifications)!'无'}</p>
                                               <p class="wproduct-other">代码：${(wish.product.goodsNum)!'无'}</p>
                                               <p class="wproduct-other">原产地：${(wish.product.originPlace)!'无'}</p>
                                               <#--<p class="wproduct-other">方法学：${(wish.product.methodology)!'无'}</p>-->
                                           </div>
                                       </a>
                                   </div>
                                   <div class="wproduct-advisory">
                                       <button class="btn btn-advisory visible-xs-block" role="button" data-content="${(wish.product.id)!''}" data-toggle="modal" data-target="#typeModal">咨询</button>
                                       <button class="btn btn-advisory hidden-xs" role="button" data-content="${(wish.product.id)!''}" data-toggle="modal" data-target="#typeModal">立即咨询</button>
                                   </div>
                               </div>
                               <div class="wish-item-control">
                                   <p class="wish-time">
                                       收藏时间：${wish.createdTime?string('yyyy-MM-dd hh:mm:ss')}
                                   </p>
                                   <button data-content="${wish.id}" class="btn" role="button" data-toggle="modal" data-target="#deleteOneModal">
                                       <img src="http://img.bnibt.com/static/delete-btn.png" alt="">
                                   </button>
                               </div>
                           </div>
                       </#list>
                   </div>
                   <#if page??>
                       <div class="mobile-more wish-more visible-xs-block">
                           <a href="#" class="btn previous">上一页</a>
                           <a href="#" class="btn next">下一页</a>
                       </div>
                       <div class="wish-pages hidden-xs">
                           <nav aria-label="Page navigation">
                               <ul class="pagination" content="${page.page?c}/${page.totalPage?c}/${page.limit?c}/${page.totalItem?c}">
                                   <div class="page-total">
                                       <p>${page.totalItem?c}条数据，共${page.totalPage?c}页</p>
                                   </div>
                                   <li class="previous-page">
                            <span aria-label="Previous">
                                上一页
                            </span>
                                   </li>
                                   <#if page.totalItem != 0>
                                       <#list 1..page.totalPage as item>
                                           <li class="${(page.page == item)?string("active", "")} pageIndex">
                                               <a role="button" href="#">${item?c}</a>
                                           </li>
                                       </#list>
                                   </#if>
                                   <li class="next-page ${(page.page == page.totalPage)?string("disabled", "")}">
                                       <a href="#" aria-label="Next">
                                           下一页
                                       </a>
                                   </li>
                               </ul>
                           </nav>
                       </div>
                   </#if>
               <#else>
                   <div class="wish-none">
                       <img src="http://img.bnibt.com/static/img_none.png" alt="">
                       <p>暂无相关内容</p>
                   </div>
               </#if>
           </div>
           <!------------------------- 推荐产品 ----------------------->
           <#if products?? && products?size gt 0>
               <div class="products">
                   <p class="products-title">
                       推荐产品
                   </p>
                   <div class="product-items">
                       <#list products as product>
                           <a hidefocus="true" title="${product.title?html}" href="${Language.lans[lan].url_path}products/${product.id}.html" class="product-item">
                               <img src="${(product.bannerList[0].resourceUrl)!''}${(product.bannerList[0].params)!''}" alt="${product.title?html}">

                               <p class="product-name">
                                   ${product.title?html}
                               </p>
                           </a>
                       </#list>
                   </div>
               </div>
           </#if>
       </div>

    </div>
    <!-- 删除单个弹窗 -->
    <div class="modal fade" id="deleteOneModal" tabindex="-1" role="dialog" aria-labelledby="deleteOneModalLabel">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <img src="http://img.bnibt.com/static/delete_icon.png" alt="">
                    <p>确定删除该商品吗</p>
                </div>
                <div class="modal-footer">
                    <button class="btn delete-cancel" data-dismiss="modal" aria-label="Close">取消</button>
                    <button class="btn delete-confirm" data-dismiss="modal" aria-label="Close">删除</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 选择咨询的客户类型 -->
    <div class="modal fade" id="typeModal" tabindex="-1" role="dialog" aria-labelledby="typeModalLabel">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">${Language.lans[lan].modal_advisory_btn_close}</span></button>
                    <h4 class="modal-title" id="typeModalLabel">${Language.lans[lan].modal_type_title}</h4>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" data-content="OTHERS" aria-label="Close">${Language.lans[lan].modal_type_customer}</button>
                    <button class="btn" data-dismiss="modal" data-content="HOSPITAL" aria-label="Close">${Language.lans[lan].modal_type_hospital}</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 删除全部弹窗 -->
    <div class="modal fade" id="deleteAllModal" tabindex="-1" role="dialog" aria-labelledby="deleteAllModalLabel">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <img src="http://img.bnibt.com/static/delete_icon.png" alt="">
                    <p>确定删除全部商品吗</p>
                </div>
                <div class="modal-footer">
                    <button class="btn delete-cancel" data-dismiss="modal" aria-label="Close">取消</button>
                    <button class="btn delete-confirm" data-dismiss="modal" aria-label="Close">删除</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 咨询弹窗 -->
    <div class="modal fade" id="advisoryModal" tabindex="-1" role="dialog" aria-labelledby="advisoryModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">关闭</span></button>
                    <h4 class="modal-title" id="advisoryModalLabel">在线咨询</h4>
                </div>
                <div class="modal-body">
                    <div class="advisory-msg">
                        <div class="message-load">
                            <img src="http://img.bnibt.com/static/loading2.gif" alt="">
                        </div>
                        <div class="msg-list">
                        </div>
                    </div>
                    <div class="advisory-msg-control">
                        <div class="send-input">
                            <input type="text" class="form-control" id="messageText" value="请在这里输入..." placeholder="请在这里输入...">
                        </div>
                        <div class="send-btn">
                            <button class="btn send" role="button">发送</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@base.base_container>