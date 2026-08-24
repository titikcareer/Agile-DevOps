using main.Components;
using main.Services;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.SignalR;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddRazorComponents()
    .AddInteractiveServerComponents();
builder.Services.AddSingleton<DeclarationStore>();
builder.Services.AddSingleton<UserAccountStore>();
builder.Services.Configure<HubOptions>(options =>
{
    options.MaximumReceiveMessageSize = 40 * 1024 * 1024; // 40 MB
});

// Cookie authentication: LoginPage.razor and Logout.razor sign the cookie
// in/out during a real HTTP request (they run static SSR, no @rendermode),
// and every interactive page reads the resulting identity via cascading
// authentication state (see AddCascadingAuthenticationState below).
builder.Services.AddAuthentication(CookieAuthenticationDefaults.AuthenticationScheme)
    .AddCookie(options =>
    {
        options.LoginPath = "/login";
        options.AccessDeniedPath = "/login";
        options.ExpireTimeSpan = TimeSpan.FromHours(8);
        options.SlidingExpiration = true;
        options.Cookie.HttpOnly = true;
        options.Cookie.SameSite = SameSiteMode.Strict;
        options.Cookie.SecurePolicy = CookieSecurePolicy.Always;
    });
builder.Services.AddAuthorization(options =>
{
    options.AddPolicy(Roles.DashboardAccess, policy => policy.RequireRole(Roles.DashboardAccess));
});
builder.Services.AddCascadingAuthenticationState();

var app = builder.Build();

if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error", createScopeForErrors: true);
    app.UseHsts();
}
app.UseStatusCodePagesWithReExecute("/not-found", createScopeForStatusCodePages: true);
app.UseHttpsRedirection();

// Must run before UseAntiforgery: the antiforgery check and any [Authorize]
// checks both depend on HttpContext.User already being populated.
app.UseAuthentication();
app.UseAuthorization();

app.UseAntiforgery();

app.MapStaticAssets();
app.MapRazorComponents<App>()
    .AddInteractiveServerRenderMode();

app.Run();