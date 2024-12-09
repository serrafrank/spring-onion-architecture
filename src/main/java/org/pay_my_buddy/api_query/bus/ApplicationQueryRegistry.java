package org.pay_my_buddy.api_query.bus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.pay_my_buddy.api_query.Query;
import org.pay_my_buddy.api_query.QueryHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

@Component
public class ApplicationQueryRegistry implements QueryRegistry {

    private final Map<Class<? extends Query<?>>, QueryProvider<?>> providerMap = new HashMap<>();

    public ApplicationQueryRegistry(ApplicationContext applicationContext) {
        Stream.of(applicationContext.getBeanNamesForType(QueryHandler.class))
                .forEach(name -> register(applicationContext, name));
    }

    private void register(ApplicationContext applicationContext, String name) {
        final Class<QueryHandler<?, ?>> handlerClass = (Class<QueryHandler<?, ?>>) applicationContext.getType(name);
        final Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, QueryHandler.class);
        final Class<? extends Query<?>> queryType = (Class<? extends Query<?>>) generics[0];
        providerMap.put(queryType, new QueryProvider<>(applicationContext, handlerClass));
    }


    @Override
    public <QUERY extends Query<RESPONSE>, RESPONSE> QueryHandler<QUERY, RESPONSE> get(Class<QUERY> queryClass) {
        return (QueryHandler<QUERY, RESPONSE>) providerMap.get(queryClass).get();
    }
}
