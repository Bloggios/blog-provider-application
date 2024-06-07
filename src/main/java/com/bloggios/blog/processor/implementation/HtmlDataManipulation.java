package com.bloggios.blog.processor.implementation;

import com.bloggios.blog.payload.record.BlogImagesAndHtmlRecord;
import com.bloggios.blog.processor.Process;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.processor.implementation
 * Created_on - June 07 - 2024
 * Created_at - 13:18
 */

@Component
public class HtmlDataManipulation {

    public String process(String htmlContent) {
        Document document = Jsoup.parse(htmlContent);
        manipulateH1(document);
        manipulateH2(document);
        manipulateH3(document);
        manipulateH4(document);
        manipulateP(document);
        manipulateCenter(document);
        manipulateRight(document);
        manipulateJustify(document);
        manipulateBlockquote(document);
        manipulateLink(document);
        wrapCodeElement(document);
        manipulateImage(document);
        manipulateCenterImage(document);
        manipulateRightImage(document);
        manipulateOl(document);
        manipulateUl(document);
        manipulateIndent1(document);
        manipulateIndent2(document);
        manipulateIndent3(document);
        manipulateIndent4(document);
        manipulateIndent5(document);
        manipulateIndent6(document);
        manipulateIndent7(document);
        manipulateIndent8(document);
        return document.body().html();
    }

    private static void manipulateH1(Document document) {
        Elements h1Elements = document.select("h1");
        for (Element h1 : h1Elements) {
            h1.addClass("break-words scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl");
        }
    }

    private static void manipulateH2(Document document) {
        Elements h2Elements = document.select("h2");
        for (Element h2 : h2Elements) {
            h2.addClass("break-words scroll-m-20 pb-2 text-3xl font-semibold tracking-tight first:mt-0");
        }
    }

    private static void manipulateH3(Document document) {
        Elements h3Elements = document.select("h3");
        for (Element h3 : h3Elements) {
            h3.addClass("break-words scroll-m-20 text-2xl font-semibold tracking-tight");
        }
    }

    private static void manipulateH4(Document document) {
        Elements h4Elements = document.select("h4");
        for (Element h4 : h4Elements) {
            h4.addClass("break-words scroll-m-20 text-xl font-semibold tracking-tight");
        }
    }

    private static void manipulateP(Document document) {
        Elements pElements = document.select("p");
        for (Element p : pElements) {
            p.addClass("break-words leading-5 [&:not(:first-child)]:mt-6 text-medium md:text-large");
        }
    }

    private static void manipulateCenter(Document document) {
        Elements centerElements = document.select(".ql-align-center");
        for (Element center : centerElements) {
            center.addClass("text-center");
        }
    }

    private static void manipulateRight(Document document) {
        Elements rightElements = document.select(".ql-align-right");
        for (Element right : rightElements) {
            right.addClass("text-right");
        }
    }

    private static void manipulateJustify(Document document) {
        Elements justifyElements = document.select(".ql-align-justify");
        for (Element justify : justifyElements) {
            justify.addClass("text-justify");
        }
    }

    private static void manipulateBlockquote(Document document) {
        Elements blockquoteElements = document.select("blockquote");
        for (Element blockquote : blockquoteElements) {
            blockquote.addClass("mt-2 border-l-2 pl-6 italic");
        }
    }

    private static void manipulateLink(Document document) {
        Elements blockquoteElements = document.select("a");
        for (Element blockquote : blockquoteElements) {
            blockquote.addClass("text-blue-600 hover:underline");
        }
    }

    private static void wrapCodeElement(Document document) {
        Elements preElements = document.select("pre");

        // Wrap each <pre> element
        for (Element pre : preElements) {
            // Create the wrapping structure
            Element wrappingDiv = document.createElement("div");
            wrappingDiv.addClass("relative max-w-2xl mx-auto mt-24");

            Element innerDiv = document.createElement("div");
            innerDiv.addClass("bg-gray-900 text-white p-4 rounded-md");

            Element flexDiv = document.createElement("div");
            flexDiv.addClass("flex justify-between items-center mb-2");

            Element span = document.createElement("span");
            span.addClass("text-gray-400");
            span.text("Code:");

            Element button = document.createElement("button");
            button.addClass("code bg-gray-800 hover:bg-gray-700 text-gray-300 px-3 py-1 rounded-md");
            button.attr("data-clipboard-target", "#code");
            button.text("Copy");

            flexDiv.appendChild(span);
            flexDiv.appendChild(button);

            Element overflowDiv = document.createElement("div");
            overflowDiv.addClass("overflow-x-auto");

            // Move the <pre> element into the overflow div
            overflowDiv.appendChild(pre.clone());

            innerDiv.appendChild(flexDiv);
            innerDiv.appendChild(overflowDiv);
            wrappingDiv.appendChild(innerDiv);

            // Replace the original <pre> element with the new wrapped structure
            pre.replaceWith(wrappingDiv);
        }
    }

    private static void manipulateImage(Document document) {
        Elements blockquoteElements = document.select("img");
        for (Element blockquote : blockquoteElements) {
            blockquote.addClass("rounded-xl");
        }
    }

    private static void manipulateCenterImage(Document document) {
        Elements pTags = document.select("p.ql-align-center:has(img:only-child)");
        for (Element pTag : pTags) {
            Element imgTag = pTag.selectFirst("img:only-child");
            if (imgTag != null) {
                imgTag.addClass("mx-auto");
            }
        }
    }

    private static void manipulateRightImage(Document document) {
        Elements pTags = document.select("p.ql-align-right:has(img:only-child)");
        for (Element pTag : pTags) {
            Element imgTag = pTag.selectFirst("img:only-child");
            if (imgTag != null) {
                imgTag.addClass("ml-auto");
            }
        }
    }

    private static void manipulateOl(Document document) {
        Elements blockquoteElements = document.select("ol");
        for (Element blockquote : blockquoteElements) {
            blockquote.addClass("my-6 ml-6 list-decimal [&>li]:mt-2");
        }
    }

    private static void manipulateUl(Document document) {
        Elements blockquoteElements = document.select("ul");
        for (Element blockquote : blockquoteElements) {
            blockquote.addClass("my-6 ml-6 list-disc [&>li]:mt-2");
        }
    }

    private static void manipulateIndent1(Document document) {
        Elements centerElements = document.select(".ql-indent-1");
        for (Element center : centerElements) {
            center.addClass("pl-[2rem]");
        }
    }

    private static void manipulateIndent2(Document document) {
        Elements centerElements = document.select(".ql-indent-2");
        for (Element center : centerElements) {
            center.addClass("pl-[3rem] md:pl-[4rem]");
        }
    }

    private static void manipulateIndent3(Document document) {
        Elements centerElements = document.select(".ql-indent-3");
        for (Element center : centerElements) {
            center.addClass("pl-[4rem] md:pl-[6rem]");
        }
    }

    private static void manipulateIndent4(Document document) {
        Elements centerElements = document.select(".ql-indent-4");
        for (Element center : centerElements) {
            center.addClass("pl-[6rem] md:pl-[8rem]");
        }
    }

    private static void manipulateIndent5(Document document) {
        Elements centerElements = document.select(".ql-indent-5");
        for (Element center : centerElements) {
            center.addClass("pl-[6rem] md:pl-[10rem]");
        }
    }

    private static void manipulateIndent6(Document document) {
        Elements centerElements = document.select(".ql-indent-6");
        for (Element center : centerElements) {
            center.addClass("pl-[6rem] md:pl-[12rem]");
        }
    }

    private static void manipulateIndent7(Document document) {
        Elements centerElements = document.select(".ql-indent-7");
        for (Element center : centerElements) {
            center.addClass("pl-[7rem] md:pl-[14rem]");
        }
    }

    private static void manipulateIndent8(Document document) {
        Elements centerElements = document.select(".ql-indent-8");
        for (Element center : centerElements) {
            center.addClass("pl-[8rem] md:pl-[16rem]");
        }
    }
}
