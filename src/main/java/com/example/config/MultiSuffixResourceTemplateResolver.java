package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;

import java.util.Map;


public class MultiSuffixResourceTemplateResolver extends SpringResourceTemplateResolver {

    private static Logger logger = LoggerFactory.getLogger(MultiSuffixResourceTemplateResolver.class);

    //  store Spring ApplicationContext
    private ApplicationContext suffixApplicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        super.setApplicationContext(applicationContext);
        suffixApplicationContext = applicationContext;
    }

    /**
     * <p>
     * Compute the real resource, once the resource name has been computed using prefix, suffix, and other
     * configured artifacts.
     * </p>
     *
     * @param configuration                the engine configuration in use.
     * @param ownerTemplate                the owner template, if the resource being computed is a fragment. Might be null.
     * @param template                     the template (normally the template name, except for String templates).
     * @param resourceName                 the resource name, complete with prefix, suffix, aliases, etc.
     * @param characterEncoding            the character encoding to be used for reading the resource.
     * @param templateResolutionAttributes the template resolution attributes, if any. Might be null.
     * @return the template resource
     */
    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate, String template, String resourceName, String characterEncoding, Map<String, Object> templateResolutionAttributes) {
        // get configured suffixes which separated by commas, eg:".html,.php,.xhtml"
        // replace all spaces in suffix
        String[] suffixes = getSuffix().replaceAll(" ", "").split(",");
        // replace suffix in String resourceName
        String view = resourceName.replace(getSuffix(), "");
        Resource resource = suffixApplicationContext.getResource(view);
        if (resource.exists()) {
            resourceName = view;
            logger.info("find template: "+ resourceName);
        } else {
            // attempt all suffix
            for (String suffix : suffixes) {
                String newResourceName = view + suffix;
                resource = suffixApplicationContext.getResource(newResourceName);
                if (resource.exists()) {
                    // file in path exists
                    resourceName = newResourceName;
                    logger.info("find template: "+ resourceName);
                    break;
                }
            }
        }
        logger.info("template name is: "+ resourceName);
        // set new resourceName
        return super.computeTemplateResource(configuration, ownerTemplate, template, resourceName, characterEncoding, templateResolutionAttributes);
    }
}

