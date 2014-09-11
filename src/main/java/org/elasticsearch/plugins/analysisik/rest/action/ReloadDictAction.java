/*
   Copyright 2012 Wyhw

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package org.elasticsearch.plugins.analysisik.rest.action;

import static org.elasticsearch.rest.RestRequest.Method.GET;
import static org.elasticsearch.rest.RestStatus.OK;
import static org.elasticsearch.rest.RestStatus.INTERNAL_SERVER_ERROR;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.*;

import org.wltea.analyzer.dic.Dictionary;

public class ReloadDictAction extends BaseRestHandler {

	private static final ESLogger LOG = Loggers.getLogger(ReloadDictAction.class);
	
	@Inject
	public ReloadDictAction(Settings settings, Client client, RestController controller) {
    	super(settings, client);
    	controller.registerHandler(GET, "/_reloadikdict", this);
    	LOG.info("register /_reloadikdict/");
    }
    
    @Override
    public void handleRequest(final RestRequest request,
            final RestChannel channel, final Client client) {
        logger.info("Request /_reloadikdict");


        boolean reload = request.paramAsBoolean("reload",Boolean.FALSE);

        try {
            // Wait for trigger
            if (reload) {
            	logger.info("Request /_reloadikdict reload");
            	Dictionary.getSingleton().reloadDictionary();
            }
            
            XContentBuilder builder = createXContentBuilderForReload(request, reload);
            channel.sendResponse(new BytesRestResponse(OK, builder));
            logger.info("Request /_reloadikdict OK.");
        } catch (Exception e) {
            LOG.error("Error while handling change REST action", e);
            channel.sendResponse(new BytesRestResponse(INTERNAL_SERVER_ERROR));
        }
    }

    private XContentBuilder createXContentBuilderForReload(final RestRequest request, boolean reload) {
    	XContentBuilder builder = null;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            builder.field("ok", "true");
            builder.field("reload", reload ? "true" : "false");
            builder.endObject();
        } catch (Exception e) {
            LOG.error("Error while setting XContentBuilder from response", e);
        }
        return builder;
    }
}
