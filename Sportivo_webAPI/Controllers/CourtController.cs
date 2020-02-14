using Microsoft.AspNetCore.Mvc;
using Sportivo_webAPI.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CourtController : Controller
    {
        private readonly CourtRepository _courtRepository;
        public CourtController()
        {
            _courtRepository = new CourtRepository();
        }
        [HttpGet]
        [Route("getCourts")]
        public IActionResult Get(int companyId, int sportId)
        {
            return Ok(_courtRepository.GetAllOfCompanyForSport(companyId, sportId));
        }

        [HttpPost]
        [Route("add")]
        public IActionResult Add()
        {
            return Ok();
        }
    }
}
