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

import java.io.IOException;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.indices.IndicesService;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.XContentRestResponse;
import org.elasticsearch.rest.XContentThrowableRestResponse;
import org.elasticsearch.rest.action.support.RestXContentBuilder;
import org.wltea.analyzer.dic.Dictionary;

public class ReloadDictAction extends BaseRestHandler {
	private static final ESLogger LOG = Loggers.getLogger(ReloadDictAction.class);

    @Inject
    public ReloadDictAction(Settings settings, Client client,
            RestController controller, IndicesService indicesService) {
        super(settings, client);
        controller.registerHandler(GET, "/_plugin/reloadikdict", this);
    }


    @Override
    public void handleRequest(final RestRequest request,
            final RestChannel channel) {
        logger.debug("Request");


        boolean reload = request.paramAsBoolean("reload",Boolean.FALSE);

        try {
            // Wait for trigger
            if (reload) {
            	Dictionary.getSingleton().reloadDictionary();
            }
            
            XContentBuilder builder = createXContentBuilderForReload(request, reload);
            channel.sendResponse(new XContentRestResponse(request, OK, builder));
        } catch (Exception e) {
            LOG.error("Error while handling change REST action", e);
            try {
                channel.sendResponse(new XContentThrowableRestResponse(request, e));
            } catch (IOException e1) {
                LOG.error("Error while sending error response", e1);
            }
        }
    }

    private XContentBuilder createXContentBuilderForReload(final RestRequest request, boolean reload) {
        XContentBuilder builder = null;
        try {
            builder = RestXContentBuilder.restContentBuilder(request);
            builder.startObject();
            builder.field("ok", "true");
            builder.field("reload", "true");
            builder.endObject();
        } catch (Exception e) {
            LOG.error("Error while setting XContentBuilder from response", e);
        }
        return builder;
    }
}
