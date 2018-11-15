package com.kasia.service.message.link;

import com.kasia.service.message.Bundle;

public interface MessageLink {
    String ID_LINK = "{modelIdNegative}";
    String CREATE_ON_NULL_LINK = "{modelCreateOnNull}";

    String getLink();
    Bundle getBundle();

}
