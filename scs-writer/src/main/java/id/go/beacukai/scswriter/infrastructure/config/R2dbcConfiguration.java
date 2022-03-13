package id.go.beacukai.scswriter.infrastructure.config;

import id.go.beacukai.scswriter.application.port.incoming.converter.HeaderCreatedEventReadConverter;
import id.go.beacukai.scswriter.application.port.incoming.converter.HeaderUpdatedEventReadConverter;
import id.go.beacukai.scswriter.application.port.outgoing.converter.HeaderCreatedEventWriteConverter;
import id.go.beacukai.scswriter.application.port.outgoing.converter.HeaderUpdatedEventWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class R2dbcConfiguration {

    @Bean
    public R2dbcCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new HeaderCreatedEventReadConverter());
        converters.add(new HeaderCreatedEventWriteConverter());
        converters.add(new HeaderUpdatedEventReadConverter());
        converters.add(new HeaderUpdatedEventWriteConverter());
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters);
    }
}
