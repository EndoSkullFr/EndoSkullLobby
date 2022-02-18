package fr.bebedlastreat.endoskullnpc.utils;

public enum PearlRider {
    GREEN("http://textures.minecraft.net/texture/5cb7c21cc43dc17678ee6f16591ffaab1f637c37f4f6bbd8cea497451d76db6d"),
    RED("http://textures.minecraft.net/texture/92c2f6fa7ec530435c431572938b9feb959c42298e5554340263c65271"),
    BLUE("http://textures.minecraft.net/texture/38be8abd66d09a58ce12d377544d726d25cad7e979e8c2481866be94d3b32f");

    private String url;

    PearlRider(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
